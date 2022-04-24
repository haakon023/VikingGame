package group22.viking.game.controller.firebase;

import java.util.HashMap;
import java.util.Map;

public class Profile extends FirebaseDocument{

    public final static int NAME_MAX_CHAR = 12;

    final static String KEY_NAME = "name";
    final static String KEY_GAMES_WON = "games_won";
    final static String KEY_GAMES_LOST = "games_lost";
    final static String KEY_HIGHSCORE = "highscore";
    final static String KEY_AVATAR_ID = "avatar_id";


    private String name;
    private long avatarId;
    private long wonGames;
    private long lostGames;
    private long highscore;

    Profile(String id) {
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
    void set(String key, Object value) throws FieldKeyUnknownException {
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
    Map<String, Object> getData() {
        return new HashMap<String, Object>(){{
            put(KEY_NAME, name);
            put(KEY_AVATAR_ID, avatarId);
            put(KEY_GAMES_WON, wonGames);
            put(KEY_GAMES_LOST, lostGames);
            put(KEY_HIGHSCORE, highscore);
        }};
    }

    void addFinishedGame(boolean win, long score) {
        if(win) {
            wonGames++;
        } else {
            lostGames++;
        }
        if (score > highscore) {
            highscore = score;
        }
    }
}
