package group22.viking.game.controller.firebase;

import java.util.HashMap;
import java.util.Map;

/**
 * The GameCollection follows the concept: First update the collection locally, and THEN send it
 * to the server.
 */
public class GameCollection extends FirebaseCollection{

    private String currentGameId;
    
    public GameCollection(FirebaseInterface firebaseInterface) {
        super(firebaseInterface, new Game(), "game");
        this.currentGameId = null;
    }

    /**
     * Only call as a host (???).
     *
     * @param host
     * @param guest
     */
    public void startGame(Profile host,
                          Profile guest,
                          final OnCollectionUpdatedListener startGameListener) {
        // 1) Create game
        final Game game = new Game(host, guest, true);
        this.add(game.getId(), game);
        this.currentGameId = game.getId();

        // 2) Get server data
        firebaseInterface.get(identifier, game.getId(), new OnGetDataListener() {
            @Override
            public void onGetData(String documentId, Map<String, Object> data) {
                System.out.println("GameCollection: Game exists!");
                game.setWonGamesHost((Long) data.get(Game.KEY_HOST_WON));
                game.setWonGamesGuest((Long) data.get(Game.KEY_GUEST_WON));
                game.setIsLoaded(true);

                // 3a) Save to database
                writeGameToServer(game, startGameListener);
            }

            @Override
            public void onFailure() {
                System.out.println("GameCollection: Game does not exist yet.");
                game.setWonGamesHost(0);
                game.setWonGamesGuest(0);
                game.setIsLoaded(true);

                // 3b) Save to database
                writeGameToServer(game, startGameListener);
            }
        });
    }

    private void writeGameToServer(final Game game, final OnCollectionUpdatedListener listener) {
        Map<String, Object> gameValues = new HashMap<>();
        gameValues.put(Game.KEY_HOST_HEALTH,  game.getHealthHost());
        gameValues.put(Game.KEY_GUEST_HEALTH, game.getHealthGuest());
        gameValues.put(Game.KEY_HOST_WON,     game.getWonGamesHost());
        gameValues.put(Game.KEY_GUEST_WON,    game.getWonGamesGuest());
        gameValues.put(Game.KEY_HOST_WAVE,    game.getWaveHost());
        gameValues.put(Game.KEY_GUEST_WAVE,   game.getWaveGuest());
        gameValues.put(Game.KEY_IS_RUNNING,   game.isRunning());

        firebaseInterface.addOrUpdateDocument(
                identifier,
                game.getId(),
                gameValues,
                new OnPostDataListener() {
                    @Override
                    public void onSuccess(String documentId) {
                        System.out.println("GameCollection: Wrote game to server: " + documentId);
                        listener.onSuccess(game);
                    }

                    @Override
                    public void onFailure() {
                        System.out.println("GameCollection: Error while writing game to server.");
                        listener.onFailure();
                    }
                });
    }

    /**
     * Update own health, load to server and return new value.
     *
     * NOTE: Does not wait for server success.
     *
     * @param damage {long}
     * @return {long} new health
     */
    public long reduceOwnHealth(long damage) {
        Game game = getGame();
        long health = game.reduceOwnHealth(damage);

        Map<String, Object> gameValues = new HashMap<>();
        gameValues.put(
                game.isHost() ? Game.KEY_HOST_HEALTH : Game.KEY_GUEST_HEALTH,
                game.isHost() ? game.getHealthHost() : game.getHealthGuest()
        );

        firebaseInterface.addOrUpdateDocument(
                identifier,
                game.getId(),
                gameValues,
                new OnPostDataListener() {
                    @Override
                    public void onSuccess(String documentId) {
                        System.out.println("GameCollection: Health updated.");
                    }

                    @Override
                    public void onFailure() {
                        System.out.println("GameCollection: Failed updating health!");
                    }
                });

        return health;
    }

    /**
     * Save, that wave was completed, and send status to database.
     */
    public void waveCompleted() {
        Game game = getGame();
        game.increaseOwnWaveNumber();

        Map<String, Object> gameValues = new HashMap<>();
        gameValues.put(
                game.isHost() ? Game.KEY_HOST_WAVE : Game.KEY_GUEST_WAVE,
                game.isHost() ? game.getWaveHost() : game.getWaveGuest()
        );
        // to be sure, also update health:
        gameValues.put(
                game.isHost() ? Game.KEY_HOST_HEALTH : Game.KEY_GUEST_HEALTH,
                game.isHost() ? game.getHealthHost() : game.getHealthGuest()
        );

        firebaseInterface.addOrUpdateDocument(
                identifier,
                game.getId(),
                gameValues,
                new OnPostDataListener() {
                    @Override
                    public void onSuccess(String documentId) {
                        System.out.println("GameCollection: Wave updated.");
                    }

                    @Override
                    public void onFailure() {
                        System.out.println("GameCollection: Failed updating wave!");
                    }
                });
    }
    
    public Game getGame() {
        if (currentGameId == null) return null;
        return (Game) get(currentGameId);
    }

    public void setOpponentListener(final OnCollectionUpdatedListener listener) {
        final GameCollection that = this;
        final Game game = getGame();
        firebaseInterface.setOnValueChangedListener(identifier, game, new OnGetDataListener() {
            @Override
            public void onGetData(String documentId, Map<String, Object> data) {
                if(!(Boolean) data.get(Game.KEY_IS_RUNNING)){
                    game.setIsRunningFalse();

                    System.out.println("GameCollection: Opponent ended game.");

                    // If guest, do not read health values as they are reset by host.
                    if (!game.isHost()) {
                        firebaseInterface.removeOnValueChangedListener(game);
                        try {
                            String key = Game.KEY_GUEST_WON;
                            game.set(key, data.get(key));
                        } catch (FieldKeyUnknownException exception) {
                            // should never happen
                        }
                        listener.onSuccess(game);
                        return;
                    }
                }

                try {
                    String key = game.isHost() ? Game.KEY_GUEST_HEALTH : Game.KEY_HOST_HEALTH;
                    game.set(key, data.get(key));

                    key = game.isHost() ? Game.KEY_GUEST_WAVE : Game.KEY_HOST_WAVE;
                    game.set(key, data.get(key));
                } catch (FieldKeyUnknownException exception) {
                    // should never happen
                }
                System.out.println("GameCollection: Opponents status updated.");
                listener.onSuccess(game);
            }

            @Override
            public void onFailure() {
                System.out.println("GameCollection: Problems with listening.");
                listener.onFailure();
            }
        });
    }

    /**
     * The host only (1) finishes the game: Save wins, reset values, stop listener and save game.
     *
     * @param isWin
     */
    public void finishGame(boolean isWin) {
        Game game = getGame();

        if (!game.isHost()) {
            return;
        }

        game.finish(isWin);

        firebaseInterface.removeOnValueChangedListener(game);

        this.writeGameToServer(game, new OnCollectionUpdatedListener() {
            @Override
            public void onSuccess(FirebaseDocument document) {
                System.out.println("GameCollection: Game finished on server.");
            }

            @Override
            public void onFailure() {
                System.out.println("GameCollection: Error while finishing game.");
            }
        });
    }

    public void startGameAsGuest(Profile host, Profile guest, final OnCollectionUpdatedListener listener) {
        final Game game = new Game(host, guest, false);
        this.add(game.getId(), game);
        this.currentGameId = game.getId();

        firebaseInterface.get(identifier, game.getId(), new OnGetDataListener() {
            @Override
            public void onGetData(String documentId, Map<String, Object> data) {
                for(Map.Entry<String, Object> e : data.entrySet()) {
                    try {
                        game.set(e.getKey(), e.getValue());
                    } catch (FieldKeyUnknownException exception) {
                        exception.printStackTrace();
                    }
                }
                listener.onSuccess(game);
            }

            @Override
            public void onFailure() {
                listener.onFailure();
            }
        });
    }
}
