package group22.viking.game.controller.firebase;

import java.util.HashMap;
import java.util.Map;

/**
 * The GameCollection follows the concept: First update the collection locally, and THEN send it
 * to the server.
 */
public class GameCollection extends FirebaseCollection{

    private final static String KEY_HOST_HEALTH = "health_host";
    private final static String KEY_GUEST_HEALTH = "health_guest";
    private final static String KEY_HOST_WINS = "wins_host";
    private final static String KEY_GUEST_WINS = "wins_guest";
    private final static String KEY_PLAYING = "playing";

    private String currentGameId;
    
    public GameCollection(FirebaseInterface firebaseInterface) {
        super(firebaseInterface, new Game());
        super.name = "game";
    }

    /**
     * Only call as a host (???).
     *
     * @param host
     * @param guest
     */
    public void startGame(Profile host, Profile guest, final OnCollectionUpdatedListener listener) {
        // 1) Create game
        final Game game = new Game(host, guest, true);
        this.add(game.getId(), game);

        // 2) Get server data
        final GameCollection that = this;
        firebaseInterface.get(name, game.getId(), new OnGetDataListener() {
            @Override
            public void onGetData(String documentId, Map<String, Object> data) {
                System.out.println("GameCollection: Game exists!");
                game.setWonGamesHost((Long) data.get(Game.KEY_HOST_WON));
                game.setWonGamesGuest((Long) data.get(Game.KEY_GUEST_WON));
                game.setIsLoaded(true);

                // 3a) Save to database
                that.writeGameToServer(game, listener);
            }

            @Override
            public void onFailure() {
                System.out.println("GameCollection: Game does not exist yet.");
                game.setWonGamesHost(0);
                game.setWonGamesGuest(0);
                game.setIsLoaded(true);

                // 3b) Save to database
                that.writeGameToServer(game, listener);
            }
        });
    }

    private void writeGameToServer(final Game game, final OnCollectionUpdatedListener listener) {
        Map<String, Object> gameValues = new HashMap<>();
        gameValues.put(KEY_HOST_HEALTH,  game.getHealthHost());
        gameValues.put(KEY_GUEST_HEALTH, game.getHealthGuest());
        gameValues.put(KEY_HOST_WINS,    game.getWonGamesHost());
        gameValues.put(KEY_GUEST_WINS,   game.getWonGamesGuest());
        gameValues.put(KEY_PLAYING,      game.isRunning());

        firebaseInterface.addOrUpdateDocument(
                name,
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
                game.isHost() ? KEY_HOST_HEALTH : KEY_GUEST_HEALTH,
                game.isHost() ? game.getHealthHost() : game.getHealthGuest()
        );

        firebaseInterface.addOrUpdateDocument(
                name,
                game.getId(),
                null,
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
    
    public Game getGame() {
        return (Game) get(currentGameId);
    }

    public void activateCurrentGameListener() {
        final GameCollection that = this;
        final Game game = getGame();
        firebaseInterface.setOnValueChangedListener(name, game, new OnGetDataListener() {
            @Override
            public void onGetData(String documentId, Map<String, Object> data) {
                try {
                    String key = game.isHost() ? Game.KEY_GUEST_HEALTH : Game.KEY_HOST_HEALTH;
                    game.set(key, data.get(key));
                } catch (FieldKeyUnknownException exception) {
                    // should never happen
                }
                System.out.println("GameCollection: Opponents health updated.");
            }

            @Override
            public void onFailure() {
                System.out.println("GameCollection: Problems with listening.");
            }
        });
    }
}
