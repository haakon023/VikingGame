package group22.viking.game.controller.firebase;

import java.util.Map;

public abstract class FirebaseCollection {

    FirebaseInterface firebaseInterface;
    String name;

    public FirebaseCollection(FirebaseInterface firebaseInterface) {
        this.firebaseInterface = firebaseInterface;
    }

    public void update(String documentId, Map<String, Object> data) {

    }

    public void addEntryById(String documentId, int requestId) {

    }
}
