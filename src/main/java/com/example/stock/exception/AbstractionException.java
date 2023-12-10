package com.example.stock.exception;

public abstract class AbstractionException extends RuntimeException{
    abstract public int getStatusCode();
    abstract public String getMessage();
}
