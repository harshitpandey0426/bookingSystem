package com.lld.system.design.bookingmyshow.exceptions;

public class AlreadyExistsException extends RuntimeException{
    public AlreadyExistsException() {
        super("The lock already excists");
    }
}
