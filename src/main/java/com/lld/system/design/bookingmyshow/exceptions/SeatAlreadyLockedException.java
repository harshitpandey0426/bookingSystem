package com.lld.system.design.bookingmyshow.exceptions;

public class SeatAlreadyLockedException extends RuntimeException {
    public SeatAlreadyLockedException() {
        super("This seat is already locked");
    }
}

