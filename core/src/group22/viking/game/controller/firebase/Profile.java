package group22.viking.game.controller.firebase;


public class Profile extends FirebaseDocument{

    public final static String KEY_NAME = "name";
    public final static String KEY_GAMES_WON = "games_won";
    public final static String KEY_GAMES_LOST = "games_lost";
    public final static String KEY_AVATAR_ID = "avatar_id";

    private String name;
    private long avatarId;
    private long wonGames;
    private long lostGames;

    public Profile(String id, String name, long avatarId, long wonGames, long lostGames) {
        super(id);
        this.name = name;
        this.avatarId = avatarId;
        this.wonGames = wonGames;
        this.lostGames = lostGames;
    }

    public Profile(String id) {
        super(id);
        this.name = null;
        this.avatarId = -1;
        this.wonGames = -1;
        this.lostGames = -1;

        this.isLoaded = false;
    }

    public String getName() {
        return name;
    }

    public long getAvatarId() {
        return avatarId;
    }

    public long getWonGames() {
        return wonGames;
    }

    public long getLostGames() {
        return lostGames;
    }

    public double getScore() {
        // TODO invent a more elaborated formula :)
        return wonGames - lostGames;
    }

    public void set(String key, Object value) throws FieldKeyUnknownException {
        switch (key) {
            case KEY_NAME:
                this.name = (String) value;
                break;
            case KEY_GAMES_WON:
                this.wonGames = (Long) value;
                break;
            case KEY_GAMES_LOST:
                this.lostGames = (Long) value;
                break;
            case KEY_AVATAR_ID:
                this.avatarId = (Long) value;
                break;
            default:
                throw new FieldKeyUnknownException();
        }
    }

    public void addFinishedGame(boolean win) {
        if(win) {
            wonGames++;
        } else {
            lostGames++;
        }
    }
}
