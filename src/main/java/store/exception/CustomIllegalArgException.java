package store.exception;

public class CustomIllegalArgException extends IllegalArgumentException {
    private static final String PREFIX = "[ERROR] ";

    public CustomIllegalArgException(String message) {
        super(PREFIX + message);
    }
}
