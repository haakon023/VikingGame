package group22.viking.game.controller.firebase;

import java.util.HashMap;
import java.util.Map;

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

    public void startGame(Profile host, Profile guest) {       // REVIEW: Why are there parameters for winning?
        final Game game = new Game(host, guest);
        // 1) Get game data
        firebaseInterface.get(name, game.getId(), new OnGetDataListener() {
            @Override
            public void onSuccess(String documentId, Map<String, Object> data) {
                System.out.println("Game exists!");
                try {
                    game.set(Game.KEY_HOST_WON, data.get(Game.KEY_HOST_WON));
                    game.set(Game.KEY_GUEST_WON, data.get(Game.KEY_GUEST_WON));
                } catch (FieldKeyUnknownException exception) {
                    // Should never be the case, as the keys are explicitly mentioned.
                }
                // TODO continue with other stuff
            }

            @Override
            public void onFailure() {
                System.out.println("Game does not exist yet.");
                // TODO continue with other stuff
            }
        });
        
        // 3) Save to database
        Map<String, Object> gameValues = new HashMap<>();
        gameValues.put(KEY_HOST_HEALTH,  game.getHealthHost());
        gameValues.put(KEY_GUEST_HEALTH, game.getHealthGuest());
        gameValues.put(KEY_HOST_WINS,    game.getWonGamesHost());
        gameValues.put(KEY_GUEST_WINS,   game.getWonGamesGuest());
        gameValues.put(KEY_PLAYING,      game.isRunning());

        firebaseInterface.addOrUpdateDocument(name, game.getId(), gameValues, new OnPostDataListener() {
            @Override
            public void onSuccess(String documentId) {

            }

            @Override
            public void onFailure() {

            }
        });
    }

    
    
    public Game getGame() {
        return (Game) get(currentGameId);
    }

    public void setOnValueChangedGameListener(String gameId) {

    }
}
