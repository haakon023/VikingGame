package group22.viking.game.controller.firebase;

import java.security.PublicKey;

public class Game extends FirebaseDocument{

    private static final long INITIAL_HEALTH = 1000;

    private static final String ID_SEPARATOR = "-";

    public final static String KEY_IS_RUNNING = "is_running";
    public final static String KEY_HOST_WON = "host_won";
    public final static String KEY_GUEST_WON = "guest_won";
    public final static String KEY_HOST_HEALTH = "host_health";
    public final static String KEY_GUEST_HEALTH = "guest_health";

    private boolean isRunning;
    private boolean isHost;
    private long wonGamesHost;
    private long wonGamesGuest;
    private long healthHost;
    private long healthGuest;

    /**
     * Constructor for dummy instance.
     */
    Game() {
        super("dummy");
    }

    /**
     * New game, where players never played against each other. (With this host)
     *
     * @param host {Profile}
     * @param guest {Profile}
     */
    public Game(Profile host, Profile guest, boolean isHost) {
        super(host.getId() + ID_SEPARATOR + guest.getId());
        super.isLoaded = false;

        this.isRunning = true;
        this.isHost = isHost;
        this.wonGamesHost = -1;
        this.wonGamesGuest = -1;
        this.healthHost = INITIAL_HEALTH;
        this.healthGuest = INITIAL_HEALTH;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public boolean isHost() {
        return isHost;
    }

    public long getWonGamesHost() {
        return wonGamesHost;
    }

    public long getWonGamesGuest() {
        return wonGamesGuest;
    }

    public long getHealthGuest() {
        return healthGuest;
    }

    public long getHealthHost() {
        return healthHost;
    }

    void setWonGamesGuest(long wonGamesGuest) {
        this.wonGamesGuest = wonGamesGuest;
    }

    void setWonGamesHost(long wonGamesHost) {
        this.wonGamesHost = wonGamesHost;
    }

    synchronized long reduceOwnHealth(long damage) {
        if(isHost) {
            return healthHost -= damage;
        }
        return healthGuest -= damage;
    }

    void setIsRunningFalse() {
        this.isRunning = false;
    }

    public void set(String key, Object value) throws FieldKeyUnknownException {
        switch (key) {
            case KEY_IS_RUNNING:
                this.isRunning = (Boolean) value;
                break;
            case KEY_HOST_WON:
                this.wonGamesHost= (Long) value;
                break;
            case KEY_GUEST_WON:
                this.wonGamesGuest = (Long) value;
                break;
            case KEY_HOST_HEALTH:
                this.healthHost = (Long) value;
                break;
            case KEY_GUEST_HEALTH:
                this.healthGuest = (Long) value;
                break;
            default:
                throw new FieldKeyUnknownException(key);
        }
    }

    public void finish(boolean hostWin) {
        if(hostWin) {
            wonGamesHost++;
        } else {
            wonGamesGuest++;
        }
        isRunning = false;
        healthGuest = -1;
        healthHost = -1;
    }
}
