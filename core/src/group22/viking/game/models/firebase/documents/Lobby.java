package group22.viking.game.models.firebase.documents;

import java.util.HashMap;
import java.util.Map;

import group22.viking.game.firebase.exceptions.FieldKeyUnknownException;
import group22.viking.game.models.firebase.FirebaseDocument;

public class Lobby extends FirebaseDocument {

    public final static int ID_LENGTH = 4;

    public enum State {
        UNDEFINED("undefined"),
        OPEN("open"),
        GUEST_JOINED_AND_READY("guest_joined_ready"),
        GUEST_JOINED("guest_joined"),
        GUEST_READY("guest_ready"),
        GUEST_LEFT("guest_left"),
        RUNNING("running");

        public final String label;

        State(String label) {
            this.label = label;
        }

        public static State fromString(String text) {
            for (State state : State.values()) {
                if (state.label.equals(text)) {
                    return state;
                }
            }
            return null;
        }
    }

    final static String KEY_HOST = "host";
    final static String KEY_GUEST = "guest";
    final static String KEY_STATE = "state";

    public final static String GUEST_FIELD_DUMMY = "__open__";

    private String hostId;
    private String guestId;
    private State state;

    private boolean isHost;

    /**
     * Dummy constructor.
     */
    public Lobby() {
        super("dummy");
        this.guestId = "dummy";
        this.hostId = "dummy";
        this.state = State.UNDEFINED;
    }


    /**
     * Create new open lobby (as host)
     *
     * @param id lobby ID
     * @param hostId ID of host player
     */
    public Lobby(String id, String hostId) {
        super(id);
        this.hostId = hostId;
        this.state = State.OPEN;
        this.guestId = GUEST_FIELD_DUMMY;
        this.isHost = true;
    }

    /**
     * Create empty lobby object (as guest).
     *
     * @param documentId lobby ID
     */
    public Lobby(String documentId) {
        super(documentId);
        this.hostId = null;
        this.state = State.UNDEFINED;
        this.guestId = GUEST_FIELD_DUMMY;
        this.isHost = false;
    }

    @Override
    public void set(String key, Object value) throws FieldKeyUnknownException {
        switch (key) {
            case KEY_HOST:
                this.hostId = (String) value;
                break;
            case KEY_GUEST:
                this.guestId = (String) value;
                break;
            case KEY_STATE:
                this.state = State.fromString((String) value);
                break;
            default:
                throw new FieldKeyUnknownException(key);
        }
    }

    @Override
    public Map<String, Object> getData() {
        return new HashMap<String, Object>(){{
            put(KEY_HOST, hostId);
            put(KEY_GUEST, guestId);
            put(KEY_STATE, state.label);
        }};
    }

    public boolean isNoGuest() {
        return guestId.equals(GUEST_FIELD_DUMMY);
    }

    public boolean isJoiningPossible() {
        return guestId.equals(GUEST_FIELD_DUMMY) &&
                (state.equals(State.OPEN) || state.equals(State.GUEST_LEFT));
    }

    public boolean isGuestNotReady() {
        return state != State.GUEST_READY && state != State.GUEST_JOINED_AND_READY;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getGuestId() {
        return guestId;
    }

    public String getHostId() {
        return hostId;
    }

    public String getOwnId() {
        return isHost ? hostId : guestId;
    }

    public String getOpponentId() {
        return isHost ? guestId : hostId;
    }

    public boolean isHost() {
        return isHost;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }
}
