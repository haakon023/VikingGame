package group22.viking.game.controller.firebase;

import java.util.Map;

/**
 * The GameCollection follows the concept: First update the collection locally, and THEN send it
 * to the server.
 */
public class GameCollection extends FirebaseCollection{

    private String localStatusId; // own status
    private String opponentStatusId; // opponent status

    private boolean isHost;
    
    public GameCollection(FirebaseInterface firebaseInterface) {
        super(firebaseInterface, new PlayerStatus(), "game");
        this.opponentStatusId = null;
        this.localStatusId = null;
        this.isHost = false;
    }

    /**
     * Creates own status sending document, when ready to play.
     * Guest: Call after joining the lobby.
     * Host: Call after starting game.
     *
     * @param localPlayer
     * @param opponent
     * @param listener
     */
    public void createOwnStatus(Profile localPlayer,
                                Profile opponent,
                                final OnCollectionUpdatedListener listener)
    {
        // 1) Create document locally
        final PlayerStatus status = new PlayerStatus(localPlayer, opponent, true);
        this.add(status.getId(), status);
        this.localStatusId = status.getId();

        // 2) Get server data
        firebaseInterface.get(identifier, status.getId(), new OnGetDataListener() {
            @Override
            public void onGetData(String documentId, Map<String, Object> data) {
                System.out.println("GameCollection: PlayerStatus exists!");
                status.setWonGames((Long) data.get(PlayerStatus.KEY_WON));
                status.setIsLoaded(true);

                // 3a) Save to database
                writeStatusToServer(status, listener);
            }

            @Override
            public void onFailure() {
                System.out.println("GameCollection: PlayerStatus does not exist yet.");
                status.setWonGames(0);
                status.setIsLoaded(true);

                // 3b) Save to database
                writeStatusToServer(status, listener);
            }
        });
    }

    private void writeStatusToServer(final PlayerStatus game, final OnCollectionUpdatedListener listener) {
        firebaseInterface.addOrUpdateDocument(
                identifier,
                game.getId(),
                game.getData(),
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
     * Add listener to opponent status when starting game.
     *
     * @param localPlayer
     * @param opponent
     */
    public void addListenerToOpponentStatus(
            Profile localPlayer,
            Profile opponent,
            boolean isHost,
            final OnCollectionUpdatedListener listener)
    {
        this.isHost = isHost;

        final PlayerStatus status = new PlayerStatus(localPlayer, opponent, false);
        this.add(status.getId(), status);
        this.opponentStatusId = status.getId();

        firebaseInterface.setOnValueChangedListener(identifier, status, new OnGetDataListener() {
            @Override
            public void onGetData(String documentId, Map<String, Object> data) {
                for (Map.Entry<String, Object> e : data.entrySet()) {
                    try {
                        status.set(e.getKey(), e.getValue());
                    } catch (FieldKeyUnknownException exception) {
                        exception.printStackTrace();
                    }
                }
                System.out.println("GameCollection: Opponents status updated.");
                if(!status.isAlive()){
                    System.out.println("GameCollection: Opponent dead.");
                    finishGame(true);
                }
                listener.onSuccess(status);
            }

            @Override
            public void onFailure() {
                System.out.println("GameCollection: Problems with listening.");
                listener.onFailure();
            }
        });
    }

    /**
     *
     *
     * @param isWin
     */
    private void finishGame(boolean isWin) {
        getLocalPlayerStatus().finish(true); // mark win
        firebaseInterface.removeOnValueChangedListener(getOpponentPlayerStatus());

        this.writeStatusToServer(getLocalPlayerStatus(), new OnCollectionUpdatedListener() {
            @Override
            public void onSuccess(FirebaseDocument document) {
                System.out.println("GameCollection: PlayerStatus finished on server.");
            }

            @Override
            public void onFailure() {
                System.out.println("GameCollection: Error while finishing game.");
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
        PlayerStatus status = getLocalPlayerStatus();
        long health = status.reduceOwnHealth(damage);

        firebaseInterface.addOrUpdateDocument(
                identifier,
                status.getId(),
                status.getData(),
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
        PlayerStatus status = getLocalPlayerStatus();
        status.increaseWave();

        firebaseInterface.addOrUpdateDocument(
                identifier,
                status.getId(),
                status.getData(),
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
    
    public PlayerStatus getLocalPlayerStatus() {
        if (this.localStatusId == null) return null;
        return (PlayerStatus) get(localStatusId);
    }

    public PlayerStatus getOpponentPlayerStatus() {
        if (this.opponentStatusId == null) return null;
        return (PlayerStatus) get(opponentStatusId);
    }
}
