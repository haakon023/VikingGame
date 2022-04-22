package group22.viking.game.controller.firebase;

import java.util.HashMap;
import java.util.Map;

import group22.viking.game.ECS.components.PlayerComponent;

public class PlayerStatus extends FirebaseDocument{

    private static final String ID_SEPARATOR = "-";

    final static String KEY_IS_ALIVE = "is_alive";
    final static String KEY_WON = "won";
    final static String KEY_HEALTH = "health";

    private boolean isAlive;
    private final boolean isWriting;
    private long wonGames;
    private long health;

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
        this.health = PlayerComponent.MAX_HEALTH;
    }

    public boolean isDead() {
        return !isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public long getWonGames() {
        return wonGames;
    }

    public long getHealth() {
        return health;
    }

    void setWonGames(long wonGames) {
        this.wonGames = wonGames;
    }

    public void setHealth(long health) {
        if(!isWriting) return; // error
        this.health = health;
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
        }};
    }

    void finish(boolean win) {
        if(!isWriting) return; // error
        if(win) wonGames++;
        health = PlayerComponent.MAX_HEALTH;
    }

}
