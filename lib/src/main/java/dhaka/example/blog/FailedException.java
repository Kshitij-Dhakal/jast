package dhaka.example.blog;

class FailedException extends Exception {
    public FailedException() {
    }

    public FailedException(String message) {
        super(message);
    }

    public FailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedException(Throwable cause) {
        super(cause);
    }

    public FailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
