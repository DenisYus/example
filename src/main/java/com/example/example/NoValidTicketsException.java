package com.example.example;

public class NoValidTicketsException extends RuntimeException {
    public NoValidTicketsException(String message) {
        super(message);
    }
}
