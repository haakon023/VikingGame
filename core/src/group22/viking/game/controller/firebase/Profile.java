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

    private boolean isLoading;

    public Profile(String id, String name, int avatarId, int wonGames, int lostGames) {
        this.id = id;
        this.name = name;
        this.avatarId = avatarId;
        this.wonGames = wonGames;
        this.lostGames = lostGames;

        this.isLoading = false;
    }

    public Profile(String id) {
        this.id = id;
        this.name = null;
        this.avatarId = 0;
        this.wonGames = 0;
        this.lostGames = 0;

        this.isLoading = true;
    }

    public String getId() {
        return id;
    }

    public String getName() throws NotLoadedException {
        if(this.isLoading) throw new NotLoadedException();
        return name;
    }

    public int getAvatarId() throws NotLoadedException {
        if(this.isLoading) throw new NotLoadedException();
        return avatarId;
    }

    public int getWonGames() throws NotLoadedException {
        if(this.isLoading) throw new NotLoadedException();
        return wonGames;
    }

    public int getLostGames() throws NotLoadedException {
        if(this.isLoading) throw new NotLoadedException();
        return lostGames;
    }

    public double getScore() throws NotLoadedException {
        if(this.isLoading) throw new NotLoadedException();
        // TODO invent a more elaborated formula :)
        return wonGames - lostGames;
    }

    public void set(String key, Object value) throws Exception {
        switch (key) {
            case KEY_NAME:
                this.name = (String) value;
                break;
            case KEY_GAMES_WON:
                this.wonGames = (Integer) value;
                break;
            case KEY_GAMES_LOST:
                this.lostGames = (Integer) value;
                break;
            case KEY_AVATAR_ID:
                this.avatarId = (Integer) value;
                break;
            default:
                throw new FieldKeyUnknownException();
        }
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }
}
