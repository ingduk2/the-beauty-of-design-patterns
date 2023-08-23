package com.example.demo.chapter5.refactoring.v3;

public class IdGenerationFailureException extends RuntimeException {

    public IdGenerationFailureException(String message) {
        super(message);
    }
}
