package kr.co.tbell.mm.exception;

public class InstanceDoesNotExistException extends RuntimeException {
    public InstanceDoesNotExistException() {
        super();
    }

    public InstanceDoesNotExistException(String message) {
        super(message);
    }

    public InstanceDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstanceDoesNotExistException(Throwable cause) {
        super(cause);
    }

    protected InstanceDoesNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
