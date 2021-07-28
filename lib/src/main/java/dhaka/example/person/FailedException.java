package dhaka.example.person;

class FailedException extends Exception {
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
