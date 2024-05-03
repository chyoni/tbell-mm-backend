package kr.co.tbell.mm.exception;

public class InstanceCreationAlreadyExistsException extends RuntimeException {

    public InstanceCreationAlreadyExistsException() {
        super();
    }

    public InstanceCreationAlreadyExistsException(String message) {
        super(message);
    }

    public InstanceCreationAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstanceCreationAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    protected InstanceCreationAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
