package com.lld.system.design.bookingmyshow.model;

import com.lld.system.design.bookingmyshow.exceptions.InvalidStateException;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@Getter
public class Booking {
    private final String id;
    private final List<Seat> seatsBooked;
    private final Show show;
    private final String user;
    private BookingStatus bookingStatus;

    public Booking(@NonNull final String id, @NonNull final Show show, @NonNull final String user,
                   @NonNull final List<Seat> seatsBooked) {
        this.id = id;
        this.show = show;
        this.seatsBooked = seatsBooked;
        this.user = user;
        this.bookingStatus = BookingStatus.Created;
    }
    public boolean isConfirmed() {
        return this.bookingStatus == BookingStatus.Confirmed;
    }

    public void confirmBooking() {
        if (this.bookingStatus != BookingStatus.Created) {
            throw new InvalidStateException();
        }
        this.bookingStatus = BookingStatus.Confirmed;
    }
    public void expireBooking() {
        if (this.bookingStatus != BookingStatus.Created) {
//            throw new InvalidStateException();
        }
        this.bookingStatus = BookingStatus.Expired;
    }





}
