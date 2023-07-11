package com.example.springwebflux.exceptionhandler;


import com.example.springwebflux.exception.InputValidationException;
import com.example.springwebflux.exception.InputValidationFailedResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InputValidationExceptionHandler {

    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<InputValidationFailedResponse> handleException(InputValidationException exception) {

        InputValidationFailedResponse inputValidationFailedResponse = new InputValidationFailedResponse();

        inputValidationFailedResponse.setInput(exception.getInput());
        inputValidationFailedResponse.setErrorCode(exception.getErrorCode());
        inputValidationFailedResponse.setMessage(exception.getMessage());

        return ResponseEntity.badRequest().body(inputValidationFailedResponse);

    }

}
