package group22.viking.game.firebase.listeners;

import java.util.Map;

public interface OnGetDataListener {
    //this is for callbacks
    void onGetData(String documentId, Map<String, Object> data);
    void onFailure();
}