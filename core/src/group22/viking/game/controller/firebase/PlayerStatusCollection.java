package group22.viking.game.controller.firebase;

import java.util.Map;

/**
 * The PlayerStatusCollection follows the concept: First update the collection locally, and THEN send it
 * to the server.
 */
public class PlayerStatusCollection extends FirebaseCollection{

    private String localStatusId; // own status
    private String opponentStatusId; // opponent status

    public PlayerStatusCollection(FirebaseInterface firebaseInterface) {
        super(firebaseInterface, new PlayerStatus(), "player_status");
        this.opponentStatusId = null;
        this.localStatusId = null;
    }

    /**
     * Creates own status sending document, when ready to play.
     * Guest: Call after joining the lobby.
     * Host: Call after starting game.
     *
     * @param localPlayerId profile ID
     * @param opponentId profile ID
     * @param listener {OnCollectionUpdatedListener}
     */
    public void createOwnStatus(String localPlayerId,
                                String opponentId,
                                final OnCollectionUpdatedListener listener)
    {
        // 1) Create document locally
        final PlayerStatus status = new PlayerStatus(localPlayerId, opponentId, true);
        this.add(status.getId(), status);
        this.localStatusId = status.getId();

        // 2) Get server data
        firebaseInterface.get(identifier, status.getId(), new OnGetDataListener() {
            @Override
            public void onGetData(String documentId, Map<String, Object> data) {
                System.out.println("PlayerStatusCollection: PlayerStatus exists!");
                if(data != null) status.setWonGames((Long) data.get(PlayerStatus.KEY_WON));
                status.setIsLoaded(true);

                // 3a) Save to database
                writeStatusToServer(status, listener);
            }

            @Override
            public void onFailure() {
                listener.onFailure();
            }
        });
    }

    /**
     * Write PlayerStatus to server.
     *
     * @param playerStatus
     * @param listener
     */
    private void writeStatusToServer(final PlayerStatus playerStatus, final OnCollectionUpdatedListener listener) {
        firebaseInterface.addOrUpdateDocument(
                identifier,
                playerStatus.getId(),
                playerStatus.getData(),
                new OnPostDataListener() {
                    @Override
                    public void onSuccess(String documentId) {
                        System.out.println("PlayerStatusCollection: Wrote playerStatus to server: " + documentId);
                        listener.onSuccess(playerStatus);
                    }

                    @Override
                    public void onFailure() {
                        System.out.println("PlayerStatusCollection: Error while writing game to server.");
                        listener.onFailure();
                    }
                });
    }

    /**
     * Add listener to opponent status when starting game.
     *
     * @param localPlayerId profile ID
     * @param opponentId profile ID
     * @param listener {OnCollectionUpdatedListener}
     */
    public void loadDuelStats(
            String localPlayerId,
            String opponentId,
            final OnCollectionUpdatedListener listener)
    {
        PlayerStatus ownStatus = new PlayerStatus(localPlayerId, opponentId, true);
        this.add(ownStatus.getId(), ownStatus);
        this.localStatusId = ownStatus.getId();
        loadPlayerStatus(ownStatus, listener);

        PlayerStatus opponentStatus = new PlayerStatus(opponentId, localPlayerId, false);
        this.add(opponentStatus.getId(), opponentStatus);
        this.opponentStatusId = opponentStatus.getId();
        loadPlayerStatus(opponentStatus, listener);
    }

    /**
     * Load PlayerStatus
     *
     * @param playerStatus {PlayerStatus}
     * @param listener {OnCollectionUpdatedListener}
     */
    private void loadPlayerStatus(final PlayerStatus playerStatus, final OnCollectionUpdatedListener listener) {
        firebaseInterface.get(
                identifier,
                playerStatus.getId(),
                new OnGetDataListener() {
                    @Override
                    public void onGetData(String documentId, Map<String, Object> data) {
                        if(data == null) {
                            listener.onSuccess(playerStatus);
                            return;
                        }
                        for (Map.Entry<String, Object> e : data.entrySet()) {
                            try {
                                playerStatus.set(e.getKey(), e.getValue());
                            } catch (FieldKeyUnknownException exception) {
                                exception.printStackTrace();
                            }
                        }
                        playerStatus.setIsLoaded(true);
                        listener.onSuccess(playerStatus);
                    }

                    @Override
                    public void onFailure() {
                        listener.onFailure();
                    }
                }
        );
    }

    /**
     * Add listener to opponent status when starting game.
     *
     * @param localPlayerId profile ID
     * @param opponentId profile ID
     * @param listener {OnCollectionUpdatedListener}
     */
    public void addListenerToOpponentStatus(
            String localPlayerId,
            String opponentId,
            final OnCollectionUpdatedListener listener)
    {
        final PlayerStatus status = new PlayerStatus(opponentId, localPlayerId, false);
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
                System.out.println("PlayerStatusCollection: Opponents status updated.");
                status.setIsLoaded(true);
                listener.onSuccess(status);
            }

            @Override
            public void onFailure() {
                System.out.println("PlayerStatusCollection: Problems with listening.");
                listener.onFailure();
            }
        });
    }

    /**
     * Update own health, load to server.
     *
     * NOTE: Does not wait for server success.
     *
     * @param health {long}
     */
    public void sendHealth(long health) {
        PlayerStatus status = getLocalPlayerStatus();
        status.setHealth(health);

        writeStatusToServer(status, new OnCollectionUpdatedListener() {
                    @Override
                    public void onSuccess(FirebaseDocument document) {
                        System.out.println("PlayerStatusCollection: Health updated.");
                    }

                    @Override
                    public void onFailure() {
                        System.out.println("PlayerStatusCollection: Failed updating health!");
                    }
        });
    }

    /**
     * Finish game: Save stats, remove opponent status listener, and write to server.
     */
    public void setOpponentDeathAndFinish() {
        getLocalPlayerStatus().addWonGame();
        firebaseInterface.removeOnValueChangedListener(getOpponentPlayerStatus());

        this.writeStatusToServer(getLocalPlayerStatus(), new OnCollectionUpdatedListener() {
            @Override
            public void onSuccess(FirebaseDocument document) {
                System.out.println("PlayerStatusCollection: PlayerStatus finished on server.");
            }

            @Override
            public void onFailure() {
                System.out.println("PlayerStatusCollection: Error while finishing game.");
            }
        });
    }

    public void setOwnDeathAndFinish() {
        PlayerStatus status = getLocalPlayerStatus();

        status.setHealth(0);
        getOpponentPlayerStatus().addWonGame(); // temporary changes

        firebaseInterface.removeOnValueChangedListener(getOpponentPlayerStatus());

        writeStatusToServer(status, new OnCollectionUpdatedListener() {
            @Override
            public void onSuccess(FirebaseDocument document) {
                System.out.println("PlayerStatusCollection: Set own death.");
            }

            @Override
            public void onFailure() {
                System.out.println("PlayerStatusCollection: Failed setting own death!");
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

    public PlayerStatus getHostOrGuestPlayerStatus(boolean isHost, boolean host) {
        return isHost == host ?
                getLocalPlayerStatus() : getOpponentPlayerStatus();
    }

    public void resetGuest() {
        this.opponentStatusId = null;
    }

    public void resetHealthValues () {
        getOpponentPlayerStatus().setHealth(-1);
        getLocalPlayerStatus().setHealth(-1);
        writeStatusToServer(getLocalPlayerStatus(), new OnCollectionUpdatedListener() {
            @Override
            public void onSuccess(FirebaseDocument document) {
                // nothing
            }

            @Override
            public void onFailure() {
                // nothing
            }
        });
    }
}
