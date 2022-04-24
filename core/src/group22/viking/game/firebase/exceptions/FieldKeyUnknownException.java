package group22.viking.game.firebase.exceptions;

public class FieldKeyUnknownException extends Exception{
    public FieldKeyUnknownException(String key) {
        super("The field key '" + key +
                "' in this firebase entry does not match to any object attributes.");
    }
}
