package com.lld.system.design.bookingmyshow.exceptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException() {
        super("Bad request exception");
    }
}
