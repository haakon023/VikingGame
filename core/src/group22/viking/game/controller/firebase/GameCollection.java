package group22.viking.game.controller.firebase;

import java.util.HashMap;
import java.util.Map;

public class GameCollection extends FirebaseCollection{

    private final int MAX_HEALTH = 100;     // We won't have permanent powerups, right?

    private final static String KEY_HOST_HEALTH = "health_host";
    private final static String KEY_GUEST_HEALTH = "health_guest";
    private final static String KEY_HOST_WINS = "wins_host";
    private final static String KEY_GUEST_WINS = "wins_guest";
    private final static String KEY_PLAYING = "playing";

    public GameCollection(FirebaseInterface firebaseInterface) {
        super(firebaseInterface);
        super.name = "game";
    }

    public void startGame(int winsPlayer1, int winsPlayer2) {       // REVIEW: Why are there parameters for winning?
        Map<String, Object> game = new HashMap<>();
        game.put(KEY_HOST_HEALTH,  MAX_HEALTH);
        game.put(KEY_GUEST_HEALTH, MAX_HEALTH);
        game.put(KEY_HOST_WINS,    winsPlayer1);
        game.put(KEY_GUEST_WINS,   winsPlayer2);
        game.put(KEY_PLAYING,   true);

        firebaseInterface.addDocument(name, null, game, new OnPostDataListener() {
            @Override
            public void onSuccess(String documentId) {

            }

            @Override
            public void onFailure() {

            }
        });
    }

    public void getGame() {

    }

    public void setOnValueChangedGameListener(String gameId) {

    }
}
