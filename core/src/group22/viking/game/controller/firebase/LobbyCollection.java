package group22.viking.game.controller.firebase;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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

    public Lobby getLobby() {
        if (currentLobbyId == null) return null;
        return (Lobby) get(currentLobbyId);
    }

    public void createLobby(
            final Profile profile,
            final OnCollectionUpdatedListener listener)
    {
        final String lobbyId = generateId();
        System.out.println(validateId(lobbyId));
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
                System.out.println("Lobby Collection: error when reading.");
                listener.onFailure();
            }
        });
    }

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
                        System.out.println("LobbyCollection: new lobby not added to server.");
                        listener.onFailure();
                    }
                }
        );
    }

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
                System.out.println("LobbyCollection: listener on lobby not set.");
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
                    System.out.println("LobbyCollection: Lobby is not open.");
                    listener.onFailure();
                    return;
                }
                add(lobby.getId(), lobby);
                currentLobbyId = lobby.getId();
                joinLobby(lobby, profile, listener);
            }

            @Override
            public void onFailure() {
                System.out.println("LobbyCollection: No lobby found.");
                listener.onFailure();
            }
        });
    }

    private void joinLobby(
            final Lobby lobby,
            final Profile profile,
            final OnCollectionUpdatedListener listener)
    {
        lobby.setGuestId(profile.getId());
        lobby.setState(Lobby.State.GUEST_READY);

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
                        System.out.println("LobbyCollection: Joining lobby failed.");
                        listener.onFailure();
                    }
                }
        );
    }

    /**
     * Set lobby to started
     *
     * @param listener {OnCollectionUpdatedListener}
     */
    public void setLobbyToStarted(final OnCollectionUpdatedListener listener) {
        final Lobby lobby = getLobby();

        if(!lobby.getState().equals(Lobby.State.GUEST_READY)) {
            System.out.println("LobbyCollection: Guest not ready. (Lobby state should handle this before)");
            listener.onFailure();
        }

        lobby.setState(Lobby.State.RUNNING);

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
                        System.out.println("LobbyCollection: Set Lobby to started failed.");
                        listener.onFailure();
                    }
                }
        );
    }

    /**
     * Update lobby after game ended
     *
     * @param listener {OnCollectionUpdatedListener}
     */
    public void setLobbyToGameEnded(final OnCollectionUpdatedListener listener) {
        final Lobby lobby = getLobby();

        Map<String, Object> lobbyValues = new HashMap<String, Object>(){{
            put(Lobby.KEY_STATE, Lobby.State.GUEST_JOINED);
        }};

        firebaseInterface.addOrUpdateDocument(identifier, lobby.getId(), lobbyValues, new OnPostDataListener() {
            @Override
            public void onSuccess(String documentId) {
                lobby.setState(Lobby.State.GUEST_JOINED);
                listener.onSuccess(lobby);
            }

            @Override
            public void onFailure() {
                System.out.println("LobbyCollection: Set Lobby to started failed.");
                listener.onFailure();
            }
        });
    }

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
                        System.out.println("Lobby Collection: Failed leaving lobby.");
                        listener.onFailure();
                    }
                }
        );
    }

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
}

