package com.lld.system.design.bookingmyshow.service;

import com.lld.system.design.bookingmyshow.exceptions.BadRequestException;
import com.lld.system.design.bookingmyshow.model.Booking;
import com.lld.system.design.bookingmyshow.helper.SeatBookingLock;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


public class PaymentsService {

    Map<Booking, Integer> bookingFailures;
    private final Integer allowedRetries;
    private final SeatBookingLock seatBookingLockProvider;

    public PaymentsService(@NonNull final Integer allowedRetries, SeatBookingLock seatBookingLockProvider) {
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
