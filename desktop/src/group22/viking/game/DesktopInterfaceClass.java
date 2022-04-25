package group22.viking.game;

import java.util.Map;

import group22.viking.game.models.firebase.FirebaseDocument;
import group22.viking.game.firebase.FirebaseInterface;
import group22.viking.game.firebase.listeners.OnGetDataListener;
import group22.viking.game.firebase.listeners.OnPostDataListener;

public class DesktopInterfaceClass implements FirebaseInterface {

    public DesktopInterfaceClass() {
    }

    @Override
    public void removeDocument(String collection, FirebaseDocument document, OnPostDataListener listener) {

    }

    @Override
    public boolean isOnline() {
        return false;
    }

    @Override
    public void setOnValueChangedListener(String collection, FirebaseDocument document, OnGetDataListener listener) {

    }

    @Override
    public void removeOnValueChangedListener(FirebaseDocument document) {

    }

    @Override
    public void addOrUpdateDocument(String collection, String document_id, Map<String, Object> values, OnPostDataListener listener) {

    }

    @Override
    public void addDocumentWithGeneratedId(String collection, Map<String, Object> values, OnPostDataListener listener) {

    }

    @Override
    public void get(String collection, String documentId, OnGetDataListener listener) {

    }

    @Override
    public void getAll(String collection, String orderBy, int limit, OnGetDataListener listener) {

    }
}
