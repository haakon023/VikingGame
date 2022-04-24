package group22.viking.game.models.firebase;

import java.util.Map;

import group22.viking.game.firebase.exceptions.FieldKeyUnknownException;

/**
 * Refers to data in database.
 */
public abstract class FirebaseDocument {

    private final String id;

    protected boolean isLoaded;

    public FirebaseDocument(String id) {
        this.id = id;
        this.isLoaded = true;
    }

    public String getId() {
        return id;
    }

    // might be of use in future (debugging server sync issues)
    public boolean isLoaded() {
        return isLoaded;
    }

    public void setIsLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }

    /**
     * Set value of document.
     *
     * @param key {String} document key
     * @param value {Object} String or Wrapper class e.g. Integer
     * @throws FieldKeyUnknownException
     */
    public abstract void set(String key, Object value) throws FieldKeyUnknownException;

    public abstract Map<String, Object> getData();
}
