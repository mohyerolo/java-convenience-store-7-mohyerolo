package store.exception;

public class CustomFileException extends IllegalStateException {
    private static final String PREFIX = "[ERROR] ";
    
    public CustomFileException(final String message) {
        throw new IllegalStateException(PREFIX + message);
    }
}
