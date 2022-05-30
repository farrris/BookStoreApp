package com.example.bookstore.exception;

public class NotEnoughProductsException extends Exception {
    public NotEnoughProductsException(String message) {
        super(message);
    }
}
