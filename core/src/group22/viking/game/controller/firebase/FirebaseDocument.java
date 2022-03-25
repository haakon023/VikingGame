package group22.viking.game.controller.firebase;

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
}
