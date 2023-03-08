package by.yatsukovich.exception.dao;

public class FailedCreateEntityException extends RuntimeException {
    public FailedCreateEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedCreateEntityException(String message) {
        super(message);
    }

    public FailedCreateEntityException(Throwable cause) {
        super(cause);
    }
}
