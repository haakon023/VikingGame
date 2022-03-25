package group22.viking.game.controller.firebase;

import java.util.Map;

public interface OnGetDataListener {
    //this is for callbacks
    void onSuccess(String documentId, Map<String, Object> data);
    void onFailure();
}