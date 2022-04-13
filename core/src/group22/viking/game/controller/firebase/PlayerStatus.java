package group22.viking.game.controller.firebase;

import java.util.HashMap;
import java.util.Map;

public class PlayerStatus extends FirebaseDocument{

    private static final long INITIAL_HEALTH = 1000;

    private static final String ID_SEPARATOR = "-";

    final static String KEY_IS_ALIVE = "is_alive";
    final static String KEY_WON = "won";
    final static String KEY_HEALTH = "health";
    final static String KEY_WAVE = "wave";

    private boolean isAlive;
    private final boolean isWriting;
    private long wonGames;
    private long health;
    private long wave;

    /**
     * Constructor for dummy instance.
     */
    PlayerStatus() {
        super("dummy");
        this.isWriting = false; //mandatory
    }

    /**
     * New game, where players never played against each other. (With this host)
     *
     * @param writer {String}
     * @param listener {String}
     */
    PlayerStatus(String writer, String listener, boolean isWriting) {
        super(writer + ID_SEPARATOR + listener);
        super.isLoaded = false;
        this.isWriting = isWriting;
        this.isAlive = true;
        this.wonGames = 0;
        this.health = INITIAL_HEALTH;
        this.wave = 0;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public long getWonGames() {
        return wonGames;
    }

    public long getHealth() {
        return health;
    }

    public long getWave() {
        return wave;
    }

    void setWonGames(long wonGames) {
        this.wonGames = wonGames;
    }

    synchronized long reduceOwnHealth(long damage) {
        if(!isWriting) return -1L; // error
        return health -= damage;
    }

    void setIsAliveFalse() {
        this.isAlive = false;
    }

    @Override
    void set(String key, Object value) throws FieldKeyUnknownException {
        switch (key) {
            case KEY_IS_ALIVE:
                this.isAlive = (Boolean) value;
                break;
            case KEY_WON:
                this.wonGames= (Long) value;
                break;
            case KEY_HEALTH:
                this.health = (Long) value;
                break;
            case KEY_WAVE:
                this.wave = (Long) value;
                break;
            default:
                throw new FieldKeyUnknownException(key);
        }
    }

    @Override
    Map<String, Object> getData() {
        return new HashMap<String, Object>(){{
            put(KEY_IS_ALIVE, isAlive);
            put(KEY_WON, wonGames);
            put(KEY_HEALTH, health);
            put(KEY_WAVE, wave);
        }};
    }

    void finish(boolean win) {
        if(!isWriting) return; // error
        wonGames++;
        health = -1;
    }

    void increaseWave() {
        wave++;
    }
}
