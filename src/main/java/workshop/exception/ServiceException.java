package workshop.exception;

/**
 * Application exception that wraps exception caused in Service layer.
 */
public class ServiceException extends AppException {

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
