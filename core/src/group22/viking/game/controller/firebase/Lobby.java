package group22.viking.game.controller.firebase;

public class Lobby extends FirebaseDocument{

    public final static String KEY_HOST = "host";
    public final static String KEY_GUEST = "guest";

    public final static String GUEST_FIELD_DUMMY = "__open__";

    private String hostId;
    private String guestId;

    /**
     * Dummy constructor.
     */
    public Lobby() {
        super("dummy");
        this.guestId = "dummy";
        this.hostId = "dummy";
    }

    public Lobby(String id, String hostId) {
        super(id);
        this.hostId = hostId;
        this.guestId = GUEST_FIELD_DUMMY;
    }

    public void addGuestId(String guestId) {
        this.guestId = guestId;
    }

    public boolean isFull() {
        return guestId != GUEST_FIELD_DUMMY;
    }
}
