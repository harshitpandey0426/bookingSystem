package com.lld.system.design.bookingmyshow.Utilities;

import com.lld.system.design.bookingmyshow.model.Seat;
import com.lld.system.design.bookingmyshow.model.Show;

import java.util.List;

public interface SeatBookingLockStrategy {
    void lockSeats(Show show, List<Seat> seat, String user);
    void unlockSeats(Show show, List<Seat> seat, String user);
    boolean validateLock(Show show, Seat seat, String user);
    List<Seat> getLockedSeats(Show show);
}
