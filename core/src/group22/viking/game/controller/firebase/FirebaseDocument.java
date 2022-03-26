package group22.viking.game.controller.firebase;

/**
 * Refers to data in database.
 */
public abstract class FirebaseDocument {

    private final String id;

    boolean isLoaded;

    public FirebaseDocument(String id) {
        this.id = id;
        this.isLoaded = true;
    }

    public String getId() {
        return id;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setIsLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }

    public void set(String key, Object value) throws FieldKeyUnknownException {
        throw new FieldKeyUnknownException(key);
    }
}
