package com.lld.system.design.bookingmyshow.exceptions;

public class InvalidStateException extends RuntimeException{
    public InvalidStateException() {
        super("Invalid state exception");
    }
}
