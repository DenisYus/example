package com.example.example;
import java.io.IOException;

public class DataReadException extends IOException {

    public DataReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
