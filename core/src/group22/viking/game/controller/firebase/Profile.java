package group22.viking.game.controller.firebase;

/**
 * Refers to data in database.
 */
public class Profile {

    public final static String KEY_NAME = "name";
    public final static String KEY_GAMES_WON = "games_won";
    public final static String KEY_GAMES_LOST = "games_lost";
    public final static String KEY_AVATAR_ID = "avatar_id";

    private String id;
    private String name;
    private int avatarId;
    private int wonGames;
    private int lostGames;

    public Profile(String id, String name, int avatarId, int wonGames, int lostGames) {
        this.id = id;
        this.name = name;
        this.avatarId = avatarId;
        this.wonGames = wonGames;
        this.lostGames = lostGames;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public int getWonGames() {
        return wonGames;
    }

    public int getLostGames() {
        return lostGames;
    }

    public double getScore() {
        // TODO invent a more elaborated formula :)
        return wonGames - lostGames;
    }

    public void set(String key, Object value) throws Exception {
        switch (key) {
            case KEY_NAME:
                this.name = (String) value;
                break;
            case KEY_GAMES_WON:
                this.wonGames = ((Integer) value).intValue();
                break;
            case KEY_GAMES_LOST:
                this.lostGames = ((Integer) value).intValue();
                break;
            case KEY_AVATAR_ID:
                this.avatarId = ((Integer) value).intValue();
                break;
            default:
                throw new Exception("Key in database unknown");
        }
    }
}
