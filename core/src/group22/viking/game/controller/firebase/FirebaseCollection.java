package group22.viking.game.controller.firebase;

public abstract class FirebaseCollection {

    FirebaseInterface firebaseInterface;
    String name;

    public FirebaseCollection(FirebaseInterface firebaseInterface) {
        this.firebaseInterface = firebaseInterface;
    }
}
