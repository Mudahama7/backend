package backend.exception;

import backend.exception.type_exception.AddInvalidUserException;
import backend.exception.type_exception.AffairNotFoundException;
import backend.exception.type_exception.SignatureMissMatchException;
import backend.exception.type_exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandling {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFoundException(UserNotFoundException ex)
    {
        ApiError apiError = new ApiError();
        apiError.setMessage(ex.getMessage());
        apiError.setCode(HttpStatus.NOT_FOUND.value());
        apiError.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AddInvalidUserException.class)
    public ResponseEntity<ApiError> handleAddInvalidUserException(AddInvalidUserException e)
    {
        ApiError apiError = new ApiError();
        apiError.setMessage(e.getMessage());
        apiError.setCode(HttpStatus.BAD_REQUEST.value());
        apiError.setTimestamp(LocalDateTime.now());

        return  new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SignatureMissMatchException.class)
    public ResponseEntity<ApiError> handleSignatureMissMatchException(SignatureMissMatchException e)
    {
        ApiError apiError = new ApiError();
        apiError.setMessage(e.getMessage());
        apiError.setCode(HttpStatus.BAD_REQUEST.value());
        apiError.setTimestamp(LocalDateTime.now());

        return  new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AffairNotFoundException.class)
    public ResponseEntity<ApiError> handleAffairNotFoundException(AffairNotFoundException e)
    {
        ApiError apiError = new ApiError();
        apiError.setMessage(e.getMessage());
        apiError.setCode(HttpStatus.NOT_FOUND.value());
        apiError.setTimestamp(LocalDateTime.now());

        return  new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

}