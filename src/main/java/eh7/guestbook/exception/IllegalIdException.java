package eh7.guestbook.exception;

public class IllegalIdException extends RuntimeException {
    public IllegalIdException() {
    }

    public IllegalIdException(String message) {
        super(message);
    }

    public IllegalIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalIdException(Throwable cause) {
        super(cause);
    }

    public IllegalIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
