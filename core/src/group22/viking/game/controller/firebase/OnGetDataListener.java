package group22.viking.game.controller.firebase;


public interface OnGetDataListener {
    //this is for callbacks
    void onSuccess(String documentId);
    void onFailure();
}