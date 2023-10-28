package com.lld.system.design.bookingmyshow.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException() {
        super("Not found exception");
    }
}
