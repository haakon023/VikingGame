package group22.viking.game.controller.firebase;

public class FieldKeyUnknownException extends Exception{
    public FieldKeyUnknownException() {
        super("The field key in this firebase entry does not match to any object attributes.");
    }
}
