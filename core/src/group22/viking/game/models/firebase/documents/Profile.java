package group22.viking.game.models.firebase.documents;

import java.util.HashMap;
import java.util.Map;

import group22.viking.game.firebase.exceptions.FieldKeyUnknownException;
import group22.viking.game.models.firebase.FirebaseDocument;

public class Profile extends FirebaseDocument {

    public final static int NAME_MAX_CHAR = 12;

    public final static String KEY_NAME = "name";
    public final static String KEY_GAMES_WON = "games_won";
    public final static String KEY_GAMES_LOST = "games_lost";
    public final static String KEY_HIGHSCORE = "highscore";
    public final static String KEY_AVATAR_ID = "avatar_id";


    private String name;
    private long avatarId;
    private long wonGames;
    private long lostGames;
    private long highscore;

    public Profile(String id) {
        super(id);
        this.name = null;
        this.avatarId = -1;
        this.wonGames = -1;
        this.lostGames = -1;
        this.highscore = -1;

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

    public long getHighscore() {
        return highscore;
    }

    public void setHighscore(long highscore) {
        this.highscore = highscore;
    }

    @Override
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
            case KEY_HIGHSCORE:
                this.highscore = (Long) value;
                break;
            case KEY_AVATAR_ID:
                this.avatarId = (Long) value;
                break;
            default:
                throw new FieldKeyUnknownException(key);
        }
    }

    @Override
    public Map<String, Object> getData() {
        return new HashMap<String, Object>(){{
            put(KEY_NAME, name);
            put(KEY_AVATAR_ID, avatarId);
            put(KEY_GAMES_WON, wonGames);
            put(KEY_GAMES_LOST, lostGames);
            put(KEY_HIGHSCORE, highscore);
        }};
    }
}
