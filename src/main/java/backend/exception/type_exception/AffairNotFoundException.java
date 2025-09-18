package backend.exception.type_exception;

public class AffairNotFoundException extends RuntimeException {
    public AffairNotFoundException(String message) {
        super(message);
    }
}
