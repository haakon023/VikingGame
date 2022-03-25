package group22.viking.game.controller.firebase;

/**
 * Tutorial: https://www.youtube.com/watch?v=WhuWqWVJ-_Y
 */

import java.util.Map;

public interface FirebaseInterface {

    void setOnValueChangedListener(String collection, String document_id);

    void addDocument(String collection, String document_id, Map<String, Object> values);

    void addDocumentWithGeneratedId(String collection,
                                    Map<String, Object> values,
                                    OnPostDataListener listener);

    void update(String collection, String document_id, Map<String, Object> values);

    void get(String collection,
             String documentId,
             OnGetDataListener listener);

    void getAll(String collection);
}
