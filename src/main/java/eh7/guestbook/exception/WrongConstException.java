package eh7.guestbook.exception;

public class WrongConstException extends RuntimeException {
    public WrongConstException() {
    }

    public WrongConstException(String message) {
        super(message);
    }

    public WrongConstException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongConstException(Throwable cause) {
        super(cause);
    }

    public WrongConstException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
