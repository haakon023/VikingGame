package group22.viking.game.controller.firebase;

public interface OnCollectionUpdatedListener {
    void onSuccess(Profile profile);
    void onFailure();
}
