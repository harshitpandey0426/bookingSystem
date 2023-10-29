package com.lld.system.design.bookingmyshow.service;

import com.lld.system.design.bookingmyshow.model.Seat;
import com.lld.system.design.bookingmyshow.model.Show;
import com.lld.system.design.bookingmyshow.Utilities.SeatBookingLockStrategy;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SeatAvailabilityService {
    private final BookingService bookingService;
    private final SeatBookingLockStrategy seatBookingLockProvider;

    public SeatAvailabilityService(@NonNull final BookingService bookingService,
                                   @NonNull final SeatBookingLockStrategy seatBookingLockProvider) {
        this.bookingService = bookingService;
        this.seatBookingLockProvider = seatBookingLockProvider;
    }
    public List<Seat> getAvailableSeats(@NonNull final Show show) {
        final List<Seat> allSeats = show.getScreen().getSeats();
        final List<Seat> unavailableSeats = getUnavailableSeats(show);

        final List<Seat> availableSeats = new ArrayList<>(allSeats);
        availableSeats.removeAll(unavailableSeats);
        return availableSeats;
    }
    public List<Seat> getUnavailableSeats(@NonNull final Show show){
        final List<Seat> unavailableSeats = bookingService.getBookedSeats(show);
        unavailableSeats.addAll(seatBookingLockProvider.getLockedSeats(show));
        return unavailableSeats;
    }
}
