package dhaka.example;

class FailedException extends RuntimeException {
    FailedException(String message) {
        super(message);
    }

    public FailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedException(Throwable cause) {
        super(cause);
    }
}
