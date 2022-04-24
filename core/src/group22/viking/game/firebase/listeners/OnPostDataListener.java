package group22.viking.game.firebase.listeners;

public interface OnPostDataListener {
    void onSuccess(String documentId);
    void onFailure();
}