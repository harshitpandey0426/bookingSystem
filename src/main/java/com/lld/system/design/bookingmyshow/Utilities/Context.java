package com.lld.system.design.bookingmyshow.Utilities;

import com.lld.system.design.bookingmyshow.model.Seat;
import com.lld.system.design.bookingmyshow.model.Show;

import java.util.List;

public class Context {
    SeatBookingLockStrategy seatBookingLock;
    public Context(SeatBookingLockStrategy seatBookingLock){
        this.seatBookingLock=seatBookingLock;
    }
    void lockSeatsStrategy(Show show, List<Seat> seat, String user){
        seatBookingLock.lockSeats(show,seat,user);
    }
    void unlockSeatsStrategy(Show show, List<Seat> seat, String user){
        seatBookingLock.unlockSeats(show,seat,user);
    }
    boolean validateLockStrategy(Show show, Seat seat, String user){
        return seatBookingLock.validateLock(show,seat,user);
    }
    List<Seat> getLockedSeatsStrategy(Show show){
        return seatBookingLock.getLockedSeats(show);
    }

}
