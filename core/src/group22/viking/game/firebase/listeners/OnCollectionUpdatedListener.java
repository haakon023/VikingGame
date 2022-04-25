package group22.viking.game.firebase.listeners;

import group22.viking.game.models.firebase.FirebaseDocument;

public interface OnCollectionUpdatedListener {
    void onSuccess(FirebaseDocument document);
    void onFailure();
}
