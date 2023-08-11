package eh7.guestbook.exception;

public class IllegalConstException extends RuntimeException {
    public IllegalConstException() {
    }

    public IllegalConstException(String message) {
        super(message);
    }

    public IllegalConstException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalConstException(Throwable cause) {
        super(cause);
    }

    public IllegalConstException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
