package backend.exception.type_exception;

public class AddInvalidUserException extends RuntimeException {
    public AddInvalidUserException(String message) {
        super(message);
    }
}
