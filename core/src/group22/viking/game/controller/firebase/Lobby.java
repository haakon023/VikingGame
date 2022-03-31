package group22.viking.game.controller.firebase;

import java.util.Map;

public class Lobby extends FirebaseDocument{

    public enum State {
        UNDEFINED("undefined"),
        OPEN("open"),
        GUEST_JOINED("guest_joined"),
        GUEST_READY("guest_ready"),
        GUEST_LEFT("guest_left"),
        RUNNING("running");

        public final String label;

        State(String label) {
            this.label = label;
        }
    }

    public final static String KEY_HOST = "host";
    public final static String KEY_GUEST = "guest";
    public final static String KEY_STATE = "open";

    public final static String GUEST_FIELD_DUMMY = "__open__";

    private String hostId;
    private String guestId;
    private State state;

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
     * Create new open lobby.
     *
     * @param id
     * @param hostId
     */
    public Lobby(String id, String hostId) {
        super(id);
        this.hostId = hostId;
        this.state = State.OPEN;
        this.guestId = GUEST_FIELD_DUMMY;
    }

    public Lobby(String documentId) {
        super(documentId);
        this.hostId = null;
        this.state = State.UNDEFINED;
        this.guestId = GUEST_FIELD_DUMMY;
    }

    public void joinGuest(String guestId) {
        this.guestId = guestId;
        this.state = State.GUEST_READY;
    }

    public boolean isFull() {
        return guestId != GUEST_FIELD_DUMMY;
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
}
