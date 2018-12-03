package workshop.exception;

/**
 * Application Exception
 */
public class AppException extends Exception {

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(String message) {
        super(message);
    }

}
