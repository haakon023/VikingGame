package group22.viking.game.controller.firebase;

public interface OnPostDataListener {
    void onSuccess(String documentId);
    void onFailure();
}