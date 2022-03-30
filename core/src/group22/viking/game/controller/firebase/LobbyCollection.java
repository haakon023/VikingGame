package group22.viking.game.controller.firebase;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * The GameCollection follows the concept: First update the collection locally, and THEN send it
 * to the server.
 */
public class LobbyCollection extends FirebaseCollection{

    private String currentLobbyId;

    public final static int ID_LENGTH = 4;

    public LobbyCollection(FirebaseInterface firebaseInterface) {
        super(firebaseInterface, new Lobby());
        super.name = "lobby";
        this.currentLobbyId = null;
    }

    public Lobby getLobby() {
        if (currentLobbyId == null) return null;
        return (Lobby) get(currentLobbyId);
    }

    public void createLobby(
            final Profile profile,
            final OnCollectionUpdatedListener gotIdListener,
            final OnCollectionUpdatedListener guestJoinedListener)
    {
        final String lobbyId = generateId();
        firebaseInterface.get(name, lobbyId, new OnGetDataListener() {
            @Override
            public void onGetData(String documentId, Map<String, Object> data) {
                // try again by recursive call
                createLobby(profile, gotIdListener, guestJoinedListener);
            }

            @Override
            public void onFailure() {
                // great! lobby id does not exist!
                addNewLobby(lobbyId, profile, gotIdListener, guestJoinedListener);
            }
        });
    }

    private void addNewLobby(
            String lobbyId,
            final Profile profile,
            final OnCollectionUpdatedListener gotIdListener,
            final OnCollectionUpdatedListener guestJoinedListener)
    {
        Map<String, Object> lobbyValues = new HashMap<String, Object>() {{
            put(Lobby.KEY_HOST, profile.getId());
            put(Lobby.KEY_GUEST, Lobby.GUEST_FIELD_DUMMY);
            put(Lobby.KEY_STATE, Lobby.State.OPEN);
        }};

        firebaseInterface.addOrUpdateDocument(name, lobbyId, lobbyValues, new OnPostDataListener() {
            @Override
            public void onSuccess(String documentId) {
                Lobby lobby = new Lobby(documentId, profile.getId());
                add(documentId, lobby);
                currentLobbyId = documentId;

                addWaitForJoinListener(lobby, guestJoinedListener);
                gotIdListener.onSuccess(lobby);
            }

            @Override
            public void onFailure() {
                System.out.println("LobbyCollection: new lobby not added to server.");
                gotIdListener.onFailure();
            }
        });
    }

    private void addWaitForJoinListener(Lobby lobby, final OnCollectionUpdatedListener listener) {
        // TODO remove listener again, when game started
        firebaseInterface.setOnValueChangedListener(name, lobby, new OnGetDataListener() {
            @Override
            public void onGetData(String documentId, Map<String, Object> data) {
                Lobby lobby = (Lobby) get(documentId);

                String guestId = (String) data.get(Lobby.KEY_GUEST);
                if (guestId == Lobby.GUEST_FIELD_DUMMY) return;

                String stateString = (String) data.get(Lobby.KEY_STATE);
                if(Lobby.State.GUEST_LEFT.label.equals(stateString)) {
                    System.out.println("LobbyCollection: user left again");
                    listener.onFailure();
                    return;
                }

                lobby.joinGuest(guestId);

                listener.onSuccess(lobby);
            }

            @Override
            public void onFailure() {
                System.out.println("LobbyCollection: listener on lobby not set.");
                listener.onFailure();
            }
        });
    }

    public void tryToJoinLobbyById(
            final String id,
            final Profile profile,
            final OnCollectionUpdatedListener gotLobbyListener,
            final OnCollectionUpdatedListener startGameListener)
    {
        firebaseInterface.get(name, id, new OnGetDataListener() {
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
                if(lobby.getState() != Lobby.State.OPEN) {
                    System.out.println("LobbyCollection: Lobby is not open.");
                    gotLobbyListener.onFailure();
                    return;
                }
                add(lobby.getId(), lobby);
                currentLobbyId = lobby.getId();
                joinLobby(lobby, profile, gotLobbyListener, startGameListener);
            }

            @Override
            public void onFailure() {
                System.out.println("LobbyCollection: No lobby found.");
                gotLobbyListener.onFailure();
            }
        });
    }

    private void joinLobby(
            final Lobby lobby,
            final Profile profile,
            final OnCollectionUpdatedListener gotLobbyListener,
            final OnCollectionUpdatedListener startGameListener)
    {
        Map<String, Object> lobbyValues = new HashMap<String, Object>(){{
            put(Lobby.KEY_GUEST, profile.getId());
            put(Lobby.KEY_STATE, Lobby.State.GUEST_READY);
        }};

        firebaseInterface.addOrUpdateDocument(name, lobby.getId(), lobbyValues, new OnPostDataListener() {
            @Override
            public void onSuccess(String documentId) {
                lobby.joinGuest(profile.getId());
                addWaitForStartListener(lobby, startGameListener);
                gotLobbyListener.onSuccess(lobby);
            }

            @Override
            public void onFailure() {
                System.out.println("LobbyCollection: Joining lobby failed.");
                gotLobbyListener.onFailure();
            }
        });
    }

    private void addWaitForStartListener(final Lobby lobby, final OnCollectionUpdatedListener listener) {
        firebaseInterface.setOnValueChangedListener(name, lobby, new OnGetDataListener() {
            @Override
            public void onGetData(String documentId, Map<String, Object> data) {
                String stateString = (String) data.get(Lobby.KEY_STATE);

                if(!Lobby.State.RUNNING.equals(stateString)) return;

                if(!lobby.getGuestId().equals((String) data.get(Lobby.KEY_GUEST))) {
                    System.out.println("LobbyCollection: Some one else joined the lobby and started.");
                    listener.onFailure();
                    return;
                }

                lobby.setState(Lobby.State.RUNNING);
                listener.onSuccess(lobby);
            }

            @Override
            public void onFailure() {
                System.out.println("LobbyCollection: Failed placing the listener.");
                listener.onFailure();
            }
        });
    }

    private String generateId() {
        String id = "";
        int validationSum = 0;
        Random rand = new Random();
        for(int i = 0; i < ID_LENGTH - 1; i++) {
            char letter = (char) ('A' + rand.nextInt(25));
            id += letter;
            validationSum += (int) letter;
        }
        validationSum = validationSum % 25 + 'A';
        return id;
    }

    /**
     * Check immediately, if typed id is a valid id or not.
     * @param id
     * @return
     */
    private boolean validateId(String id) {
        int validationSum = 0;
        for(int i = 0; i < ID_LENGTH - 1; i++) {
            validationSum += (int) id.charAt(i);
        }
        validationSum = validationSum % 25 + 'A';

        return validationSum == id.charAt(ID_LENGTH - 1);
    }
}

