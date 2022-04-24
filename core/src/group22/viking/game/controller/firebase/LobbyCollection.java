package group22.viking.game.controller.firebase;

import java.util.Map;
import java.util.Random;
import java.util.logging.Level;

import group22.viking.game.controller.VikingGame;

/**
 * The PlayerStatusCollection follows the concept: First update the collection locally, and THEN send it
 * to the server.
 */
public class LobbyCollection extends FirebaseCollection{

    private String currentLobbyId;

    public LobbyCollection(FirebaseInterface firebaseInterface) {
        super(firebaseInterface, new Lobby(), "lobby");
        this.currentLobbyId = null;
    }

    /**
     * Get current lobby or null.
     *
     * @return
     */
    public Lobby getLobby() {
        if (currentLobbyId == null) return null;
        return (Lobby) get(currentLobbyId);
    }

    /**
     * Host tries to open new lobby.
     *
     * @param profile {Profile}
     * @param listener {OnCollectionUpdatedListener}
     */
    public void createLobby(
            final Profile profile,
            final OnCollectionUpdatedListener listener)
    {
        final String lobbyId = generateId();
        firebaseInterface.get(identifier, lobbyId, new OnGetDataListener() {
            @Override
            public void onGetData(String documentId, Map<String, Object> data) {
                if (data != null) {
                    // try again by recursive call
                    createLobby(profile, listener);
                    return;
                }
                // great! lobby id does not exist!
                addNewLobby(lobbyId, profile, listener);
            }

            @Override
            public void onFailure() {
                VikingGame.LOG.log(Level.SEVERE, "Lobby Collection: error when reading.");
                listener.onFailure();

            }
        });
    }

    /**
     * Add new lobby to server.
     *
     * @param lobbyId {String}
     * @param profile {Profile}
     * @param listener {OnCollectionUpdatedListener}
     */
    private void addNewLobby(
            String lobbyId,
            final Profile profile,
            final OnCollectionUpdatedListener listener)
    {
        final Lobby lobby = new Lobby(lobbyId, profile.getId());

        firebaseInterface.addOrUpdateDocument(
                identifier,
                lobby.getId(),
                lobby.getData(),
                new OnPostDataListener() {
                    @Override
                    public void onSuccess(String documentId) {
                        add(documentId, lobby);
                        currentLobbyId = documentId;
                        listener.onSuccess(lobby);
                    }

                    @Override
                    public void onFailure() {
                        VikingGame.LOG.log(Level.SEVERE, "LobbyCollection: new lobby not added to server.");
                        listener.onFailure();
                    }
                }
        );
    }

    /**
     * Set Listener on Lobby.
     *
     * @param lobby {Lobby}
     * @param listener {OnCollectionUpdatedListener}
     */
    public void addLobbyListener(Lobby lobby, final OnCollectionUpdatedListener listener) {
        firebaseInterface.setOnValueChangedListener(identifier, lobby, new OnGetDataListener() {
            @Override
            public void onGetData(String documentId, Map<String, Object> data) {
                Lobby lobby = (Lobby) get(documentId);
                for(Map.Entry<String, Object> e : data.entrySet()) {
                    try {
                        lobby.set(e.getKey(), e.getValue());
                    } catch (FieldKeyUnknownException exception) {
                        exception.printStackTrace();
                    }
                }
                listener.onSuccess(lobby);
            }

            @Override
            public void onFailure() {
                // lobby deleted
                listener.onFailure();
            }
        });
    }

    /**
     * User tries to join lobby by using lobby id.
     *
     * @param id lobby ID
     * @param profile guest ID
     * @param listener {OnCollectionUpdatedListener}
     */
    public void tryToJoinLobbyById(
            final String id,
            final Profile profile,
            final OnCollectionUpdatedListener listener)
    {
        firebaseInterface.get(identifier, id, new OnGetDataListener() {
            @Override
            public void onGetData(String documentId, Map<String, Object> data) {
                if(data == null) {
                    VikingGame.LOG.log(Level.INFO, "LobbyCollection: Lobby does not exist.");
                    listener.onFailure();
                    return;
                }
                Lobby lobby = new Lobby(documentId);
                for(Map.Entry<String, Object> e : data.entrySet()) {
                    try {
                        lobby.set(e.getKey(), e.getValue());
                    } catch (FieldKeyUnknownException exception) {
                        exception.printStackTrace();
                    }
                }

                // check if lobby open
                if(!lobby.isJoiningPossible()) {
                    VikingGame.LOG.log(Level.INFO, "LobbyCollection: Lobby is not open.");
                    listener.onFailure();
                    return;
                }

                // join lobby
                add(lobby.getId(), lobby);
                currentLobbyId = lobby.getId();
                lobby.setGuestId(profile.getId());
                lobby.setState(Lobby.State.GUEST_JOINED_AND_READY);
                writeLobbyToServer(lobby, listener);
            }

            @Override
            public void onFailure() {
                VikingGame.LOG.log(Level.WARNING, "LobbyCollection: No lobby found.");
                listener.onFailure();

            }
        });
    }

    /**
     * Set lobby to started.
     *
     * @param listener {OnCollectionUpdatedListener}
     */
    public void setLobbyToStarted(OnCollectionUpdatedListener listener) {
        final Lobby lobby = getLobby();

        if(lobby.isGuestNotReady()) {
            VikingGame.LOG.log(Level.INFO, "LobbyCollection: Guest not ready. (Lobby state should handle this before)");
            listener.onFailure();
            return;
        }

        lobby.setState(Lobby.State.RUNNING);
        writeLobbyToServer(lobby, listener);
    }

    /**
     * Update lobby after game ended.
     *
     * @param listener {OnCollectionUpdatedListener}
     */
    public void setLobbyToGameEnded(OnCollectionUpdatedListener listener) {
        final Lobby lobby = getLobby();
        lobby.setState(Lobby.State.GUEST_JOINED);
        writeLobbyToServer(lobby, listener);
    }

    /**
     * Host closes lobby.
     *
     * @param listener {OnCollectionUpdatedListener}
     */
    public void deleteLobby(final OnCollectionUpdatedListener listener) {
        firebaseInterface.removeOnValueChangedListener(getLobby());
        firebaseInterface.removeDocument(identifier, getLobby(), new OnPostDataListener() {
            @Override
            public void onSuccess(String documentId) {
                currentLobbyId = null;
                remove(documentId);
                listener.onSuccess(null);
            }

            @Override
            public void onFailure() {
                listener.onFailure();

            }
        });
    }

    /**
     * Guest leaves Lobby.
     *
     * @param listener {OnCollectionUpdatedListener}
     */
    public void leaveLobby(final OnCollectionUpdatedListener listener) {
        Lobby lobby = getLobby();
        lobby.setState(Lobby.State.GUEST_LEFT);
        lobby.setGuestId(Lobby.GUEST_FIELD_DUMMY);

        firebaseInterface.removeOnValueChangedListener(lobby);
        firebaseInterface.addOrUpdateDocument(
                identifier,
                lobby.getId(),
                lobby.getData(),
                new OnPostDataListener() {
                    @Override
                    public void onSuccess(String documentId) {
                        currentLobbyId = null;
                        remove(documentId);
                        listener.onSuccess(null);
                    }

                    @Override
                    public void onFailure() {
                        VikingGame.LOG.log(Level.SEVERE, "Lobby Collection: Failed leaving lobby.");
                        listener.onFailure();
                    }
                }
        );
    }

    /**
     * Guest claims that they is ready.
     *
     * @param listener
     */
    public void setGuestReady(final OnCollectionUpdatedListener listener) {
        Lobby lobby = getLobby();
        lobby.setState(Lobby.State.GUEST_READY);
        writeLobbyToServer(lobby, listener);
    }

    /**
     * Check immediately, if typed id is a valid id or not.
     *
     * @param id lobby ID to be validated
     * @return {boolean} true if correct
     */
    public boolean validateId(String id) {
        if(id.length() != Lobby.ID_LENGTH) return false;

        int validationSum = 0;
        for(int i = 0; i < Lobby.ID_LENGTH - 1; i++) {
            validationSum += id.charAt(i);
        }
        validationSum = validationSum % 25 + 'A';

        return validationSum == id.charAt(Lobby.ID_LENGTH - 1);
    }

    /**
     * Generate random lobby ID.
     *
     * @return
     */
    private String generateId() {
        StringBuilder id = new StringBuilder();
        int validationSum = 0;
        Random rand = new Random();
        for(int i = 0; i < Lobby.ID_LENGTH - 1; i++) {
            char letter = (char) ('A' + rand.nextInt(25));
            id.append(letter);
            validationSum += letter;
        }
        char validation = (char) ('A' + validationSum % 25);
        return id.toString() + validation;
    }

    /**
     * Write lobby to server.
     *
     * @param lobby {Lobby}
     * @param listener {OnCollectionUpdatedListener}
     */
    private void writeLobbyToServer(final Lobby lobby, final OnCollectionUpdatedListener listener) {
        firebaseInterface.addOrUpdateDocument(
                identifier,
                lobby.getId(),
                lobby.getData(),
                new OnPostDataListener() {
                    @Override
                    public void onSuccess(String documentId) {
                        listener.onSuccess(lobby);
                    }

                    @Override
                    public void onFailure() {
                        VikingGame.LOG.log(Level.SEVERE, "Lobby Collection: Write Lobby to Server failed.");
                        listener.onFailure();

                    }
                }
        );
    }


}

