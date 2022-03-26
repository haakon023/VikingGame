package group22.viking.game.controller.firebase;

import java.util.HashMap;
import java.util.Map;

import javax.swing.text.Document;

public abstract class FirebaseCollection {

    FirebaseInterface firebaseInterface;
    String name;

    private Map<String, FirebaseDocument> documents;
    private final FirebaseDocument documentTypeExample;

    public FirebaseCollection(FirebaseInterface firebaseInterface, FirebaseDocument documentTypeExample) {
        this.firebaseInterface = firebaseInterface;
        this.documents = new HashMap<>();
        this.documentTypeExample = documentTypeExample;
    }

    public void update(String documentId, Map<String, Object> data) {

    }

    FirebaseDocument get(String key) {
        return documents.get(key);
    }

    void add(String key, FirebaseDocument document) {
        if(document.getClass() != documentTypeExample.getClass()) {
            return;
        }
        documents.put(key, document);
    }

    boolean isKeyLocallyExisting(String key) {
        return documents.containsKey(key);
    }
}
