package group22.viking.game.controller.firebase;

/**
 *       Tutorial: https://www.youtube.com/watch?v=WhuWqWVJ-_Y
 *
 */

import java.util.Map;

public interface FirebaseInterface {

    public void setOnValueChangedListener(String collection, String document_id);

    public void addDocument(String collection, String document_id, Map<String, Object> values);

    public void addDocumentWithGeneratedId(String collection, Map<String, Object> values);

    public void update(String collection, String document_id, Map<String, Object> values);

    public void get(String collection, String document_id, FirebaseCollection firebaseCollection);

    public void getAll(String collection);
}
