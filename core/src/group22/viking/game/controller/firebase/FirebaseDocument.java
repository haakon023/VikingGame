package group22.viking.game.controller.firebase;

import java.util.Map;

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

    // might be of use in future (debugging server sync issues)
    boolean isLoaded() {
        return isLoaded;
    }

    void setIsLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }

    abstract void set(String key, Object value) throws FieldKeyUnknownException;

    abstract Map<String, Object> getData();
}
