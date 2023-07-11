package com.example.springwebflux.exception;


import lombok.Data;

@Data
public class InputValidationException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Input is not in the allowed range 10 - 20";

    private final int errorCode = 100;

    private final int input;

    public InputValidationException(int input) {
        super(ERROR_MESSAGE);
        this.input = input;
    }

}
