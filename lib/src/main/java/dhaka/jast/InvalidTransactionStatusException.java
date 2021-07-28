package dhaka.jast;

public class InvalidTransactionStatusException extends RuntimeException {
    public InvalidTransactionStatusException() {
    }

    public InvalidTransactionStatusException(String message) {
        super(message);
    }

    public InvalidTransactionStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTransactionStatusException(Throwable cause) {
        super(cause);
    }

    public InvalidTransactionStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
