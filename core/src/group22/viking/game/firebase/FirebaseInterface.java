package group22.viking.game.firebase;

import java.util.Map;

import group22.viking.game.firebase.listeners.OnGetDataListener;
import group22.viking.game.firebase.listeners.OnPostDataListener;
import group22.viking.game.models.firebase.FirebaseDocument;

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

    void removeDocument(String collection,
                        FirebaseDocument document,
                        OnPostDataListener listener);

    boolean isOnline();
}
