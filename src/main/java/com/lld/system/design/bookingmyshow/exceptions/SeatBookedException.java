package com.lld.system.design.bookingmyshow.exceptions;

public class SeatBookedException extends RuntimeException{
    public SeatBookedException() {
        super("This seat is already booked");
    }
}
