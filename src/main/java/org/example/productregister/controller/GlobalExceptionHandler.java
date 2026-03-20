package org.example.productregister.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.example.productregister.exceptions.ErrorResponse;
import org.example.productregister.exceptions.InvalidArgumentsException;
import org.example.productregister.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidArgumentsException.class)
    public ResponseEntity<ErrorResponse> handlerInvalidArgumentsException(
            InvalidArgumentsException ex,
            HttpServletRequest request){

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerNotFoundException(NotFoundException ex, HttpServletRequest request){

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request ){
       String message = ex.getBindingResult().getFieldErrors().stream()
               .map(error -> error.getField() + ": " + error.getDefaultMessage())
               .reduce((msg1, msg2) -> msg1 + "; " + msg2)
               .orElse("Validation Error");

       ErrorResponse error = new ErrorResponse(
               HttpStatus.BAD_REQUEST.value(),
               HttpStatus.BAD_REQUEST.getReasonPhrase(),
               message,
               request.getRequestURI()
       );

       return ResponseEntity.badRequest().body(error);

    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handlerInvalidArgumentsException(
            NoResourceFoundException ex,
            HttpServletRequest request){

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                "Resource not found: " + request.getRequestURI(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
