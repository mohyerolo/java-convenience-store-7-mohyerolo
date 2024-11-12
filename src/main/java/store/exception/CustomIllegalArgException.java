package store.exception;

public class CustomIllegalArgException extends IllegalArgumentException {
    private static final String PREFIX = "[ERROR] ";
    private static final String SUFFIX = " 다시 입력해 주세요.";

    public CustomIllegalArgException(final String message) {
        super(PREFIX + message + SUFFIX);
    }
}
