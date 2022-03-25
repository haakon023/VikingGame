package group22.viking.game.controller.firebase;

public interface OnCollectionUpdatedListener {
    void onSuccess(FirebaseDocument profile);
    void onFailure();
}
