package backend.exception.type_exception;

public class SignatureMissMatchException extends RuntimeException{
    public SignatureMissMatchException(String message) {
        super(message);
    }
}
