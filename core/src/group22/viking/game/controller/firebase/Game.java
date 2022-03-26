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
     * New game, where the won/lost stats are known.
     *
     * @param host {Profile}
     * @param guest {Profile}
     * @param wonGamesHost {long}
     * @param wonGamesGuest {long}
     */
    public Game(Profile host, Profile guest, long wonGamesHost, long wonGamesGuest) {
        super(host.getId() + ID_SEPARATOR + guest.getId());
        this.isRunning = true;
        this.wonGamesHost = wonGamesHost;
        this.wonGamesGuest = wonGamesGuest;
        this.healthHost = INITIAL_HEALTH;
        this.healthGuest = INITIAL_HEALTH;
    }

    /**
     * New game, where players never played against each other. (With this host)
     *
     * @param host {Profile}
     * @param guest {Profile}
     */
    public Game(Profile host, Profile guest) {
        super(host.getId() + ID_SEPARATOR + guest.getId());
        this.isRunning = true;
        this.wonGamesHost = 0;
        this.wonGamesGuest = 0;
        this.healthHost = INITIAL_HEALTH;
        this.healthGuest = INITIAL_HEALTH;
    }


    public boolean isRunning() {
        return isRunning;
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

    public void addFinishedGame(boolean hostWin) {
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
