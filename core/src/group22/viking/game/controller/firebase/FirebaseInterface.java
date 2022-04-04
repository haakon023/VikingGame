package group22.viking.game.controller.firebase;

/**
 * Tutorial: https://www.youtube.com/watch?v=WhuWqWVJ-_Y
 */

import java.util.Map;

public interface FirebaseInterface {

    void setOnValueChangedListener(String collection,
                                   FirebaseDocument document,
                                   OnGetDataListener listener);

    void removeOnValueChangedListener(FirebaseDocument document);

    void addOrUpdateDocument(String collection,
                     String document_id,
                     Map<String, Object> values,
                     OnPostDataListener listener);

    void addDocumentWithGeneratedId(String collection,
                                    Map<String, Object> values,
                                    OnPostDataListener listener);

    void get(String collection,
             String documentId,
             OnGetDataListener listener);

    void getAll(String collection,
                String orderBy,
                int limit,
                OnGetDataListener listener);
}
