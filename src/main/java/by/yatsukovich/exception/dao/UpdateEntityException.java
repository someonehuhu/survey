package by.yatsukovich.exception.dao;

public class UpdateEntityException extends RuntimeException {
    public UpdateEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateEntityException(String message) {
        super(message);
    }

    public UpdateEntityException(Throwable cause) {
        super(cause);
    }
}
