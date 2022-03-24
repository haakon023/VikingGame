package group22.viking.game.controller.firebase;

public class NotLoadedException extends Exception{
    public NotLoadedException() {
        super("The object was not loaded from the database yet.");
    }
}
