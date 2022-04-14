package group22.viking.game.controller.firebase;

import java.util.HashMap;
import java.util.Map;

public abstract class FirebaseCollection {

    FirebaseInterface firebaseInterface;
    String identifier;

    private final Map<String, FirebaseDocument> documents;
    private final FirebaseDocument documentTypeExample;

    public FirebaseCollection(
            FirebaseInterface firebaseInterface,
            FirebaseDocument documentTypeExample,
            String identifier)
    {
        this.firebaseInterface = firebaseInterface;
        this.documents = new HashMap<>();
        this.documentTypeExample = documentTypeExample;
        this.identifier = identifier;
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

    void remove(String key) {
        if(!documents.containsKey(key)) return;
        documents.remove(key);
    }

    boolean isKeyNotExistingLocally(String key) {
        return !documents.containsKey(key);
    }
}
