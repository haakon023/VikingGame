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

    public final static int ID_LENGTH = 5;

    public LobbyCollection(FirebaseInterface firebaseInterface) {
        super(firebaseInterface, new Lobby());
        super.name = "lobby";
        this.currentLobbyId = null;
    }

    public Lobby getLobby() {
        if (currentLobbyId == null) return null;
        return (Lobby) get(currentLobbyId);
    }

    public void openLobby(Profile profile, OnPostDataListener listener) {

    }

    public void joinLobby(String id, Profile profile, OnPostDataListener listener) {

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

