package com.lld.system.design.bookingmyshow.service;

import com.lld.system.design.bookingmyshow.exceptions.BadRequestException;
import com.lld.system.design.bookingmyshow.model.Booking;
import com.lld.system.design.bookingmyshow.Utilities.SeatBookingLockStrategy;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;


public class PaymentsService {

    Map<Booking, Integer> bookingFailures;
    private final Integer allowedRetries;
    private final SeatBookingLockStrategy seatBookingLockProvider;

    public PaymentsService(@NonNull final Integer allowedRetries, SeatBookingLockStrategy seatBookingLockProvider) {
        this.allowedRetries = allowedRetries;
        this.seatBookingLockProvider = seatBookingLockProvider;
        bookingFailures = new HashMap<>();
    }

    public void processPaymentFailed(@NonNull final Booking booking, @NonNull final String user) {
       if(!booking.getUser().equals(user)){ //calling user should be same as booking user
            throw new BadRequestException();
       }
        if (!bookingFailures.containsKey(booking)) {
            bookingFailures.put(booking, 0);
        }
        final Integer currentFailuresCount = bookingFailures.get(booking);
        final Integer newFailuresCount = currentFailuresCount + 1;
        bookingFailures.put(booking, newFailuresCount);
        if (newFailuresCount > allowedRetries) {
            seatBookingLockProvider.unlockSeats(booking.getShow(), booking.getSeatsBooked(), booking.getUser());
        }

    }
}
