package group22.viking.game.models.firebase.documents;

import java.util.HashMap;
import java.util.Map;

import group22.viking.game.firebase.exceptions.FieldKeyUnknownException;
import group22.viking.game.models.firebase.FirebaseDocument;

public class PlayerStatus extends FirebaseDocument {

    private static final String ID_SEPARATOR = "-";

    public final static String KEY_WON = "won";
    public final static String KEY_HEALTH = "health";

    private final boolean isWriting;
    private long wonGames;
    private long health; // -1 is default, 0 is dead, > 0 is alive

    /**
     * Constructor for dummy instance.
     */
    public PlayerStatus() {
        super("dummy");
        this.isWriting = false; //mandatory
    }

    /**
     * New game, where players never played against each other. (With this host)
     *
     * @param writer {String}
     * @param listener {String}
     */
    public PlayerStatus(String writer, String listener, boolean isWriting) {
        super(writer + ID_SEPARATOR + listener);
        super.isLoaded = false;
        this.isWriting = isWriting;
        this.wonGames = 0;
        this.health = -1;
    }

    public boolean isDead() {
        return health == 0;
    }

    public long getWonGames() {
        return wonGames;
    }

    public long getHealth() {
        return health;
    }

    public void setWonGames(long wonGames) {
        this.wonGames = wonGames;
    }

    public void setHealth(long health) {
        if(!isWriting) return; // error
        this.health = health;
    }

    @Override
    public void set(String key, Object value) throws FieldKeyUnknownException {
        switch (key) {
            case KEY_WON:
                this.wonGames= (Long) value;
                break;
            case KEY_HEALTH:
                this.health = (Long) value;
                break;
            default:
                throw new FieldKeyUnknownException(key);
        }
    }

    @Override
    public Map<String, Object> getData() {
        return new HashMap<String, Object>(){{
            put(KEY_WON, wonGames);
            put(KEY_HEALTH, health);
        }};
    }

    public void addWonGame() {
        wonGames++;
    }

}
