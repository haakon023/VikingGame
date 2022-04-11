package group22.viking.game.controller.firebase;

import java.util.HashMap;
import java.util.Map;

public class Game extends FirebaseDocument{

    private static final long INITIAL_HEALTH = 1000;

    private static final String ID_SEPARATOR = "-";

    public final static String KEY_IS_RUNNING = "is_running";
    public final static String KEY_HOST_WON = "host_won";
    public final static String KEY_GUEST_WON = "guest_won";
    public final static String KEY_HOST_HEALTH = "host_health";
    public final static String KEY_GUEST_HEALTH = "guest_health";
    public final static String KEY_HOST_WAVE = "host_wave";
    public final static String KEY_GUEST_WAVE = "guest_wave";

    private boolean isRunning;
    private boolean isHost;
    private long wonGamesHost;
    private long wonGamesGuest;
    private long healthHost;
    private long healthGuest;
    private long waveHost;
    private long waveGuest;

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
        this.waveHost = 0;
        this.waveGuest = 0;
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

    public long getWaveGuest() {
        return waveGuest;
    }

    public long getWaveHost() {
        return waveHost;
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
            case KEY_HOST_WAVE:
                this.waveHost = (Long) value;
                break;
            case KEY_GUEST_WAVE:
                this.waveGuest = (Long) value;
                break;
            default:
                throw new FieldKeyUnknownException(key);
        }
    }

    @Override
    public Map<String, Object> getData() {
        return new HashMap<String, Object>(){{
            put(KEY_IS_RUNNING, isRunning);
            put(KEY_HOST_WON, wonGamesHost);
            put(KEY_GUEST_WON, wonGamesGuest);
            put(KEY_HOST_HEALTH, healthHost);
            put(KEY_GUEST_HEALTH, healthGuest);
            put(KEY_HOST_WAVE, waveHost);
            put(KEY_GUEST_WAVE, waveGuest);
        }};
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

    public void increaseOwnWaveNumber() {
        if (isHost) {
            waveHost++;
        } else {
            waveGuest++;
        }
    }

    public boolean playNextWave() {
        return isHost ? (waveHost <= waveGuest ) : (waveHost >= waveGuest);
    }
}
