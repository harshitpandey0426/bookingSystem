package com.lld.system.design.bookingmyshow.service;

import com.lld.system.design.bookingmyshow.exceptions.BadRequestException;
import com.lld.system.design.bookingmyshow.model.Booking;
import com.lld.system.design.bookingmyshow.model.Seat;
import com.lld.system.design.bookingmyshow.model.Show;
import com.lld.system.design.bookingmyshow.helper.SeatBookingLock;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


public class BookingService {

    private final Map<String, Booking> showBookings;
    private final SeatBookingLock seatBookingLockProvider;

    @Autowired
    public BookingService(SeatBookingLock seatBookingLockProvider) {
        this.seatBookingLockProvider = seatBookingLockProvider;
        this.showBookings = new HashMap<>();
    }

    public Booking getBooking(@NonNull final String bookingId) {
        if (!showBookings.containsKey(bookingId)) {
//            throw new NotFoundException();
        }
        return showBookings.get(bookingId);
    }



    //ok
    public Booking createBooking(@NonNull final String userId, @NonNull final Show show,
                                 @NonNull final List<Seat> seats) {
        if (isAnySeatAlreadyBooked(show, seats)) {
//            throw new SeatPermanentlyUnavailableException();
        }
        seatBookingLockProvider.lockSeats(show, seats, userId);
        final String bookingId = UUID.randomUUID().toString();
        final Booking newBooking = new Booking(bookingId, show, userId, seats);
        showBookings.put(bookingId, newBooking);
        return newBooking;
    }
    private boolean isAnySeatAlreadyBooked(final Show show, final List<Seat> seats) {
        final List<Seat> bookedSeats = getBookedSeats(show);
        for (Seat seat : seats) {
            if (bookedSeats.contains(seat)) {
                return true;
            }
        }
        return false;
    }

    public List<Seat> getBookedSeats(@NonNull final Show show) {
        List<Booking> allBookings = getAllBookings(show);
        List<Seat> seatsBooked = new ArrayList<>();
        for (Booking booking : allBookings) {
            if (booking.isConfirmed()) {
                seatsBooked.addAll(booking.getSeatsBooked());
            }
        }
        return seatsBooked;
    }
    public List<Booking> getAllBookings(@NonNull final Show show) {
        List<Booking> response = new ArrayList<>();
        for (Map.Entry<String, Booking> bookings : showBookings.entrySet()) {
            Booking booking = bookings.getValue();
            if (booking.getShow().equals(show)) { //all bookings for our show
                response.add(booking);
            }
        }
        return response;
    }
    public void confirmBooking(@NonNull final Booking booking, @NonNull final String user) {
        if (!booking.getUser().equals(user)) {
            throw new BadRequestException();
        }

        for (Seat seat : booking.getSeatsBooked()) { //validate if seat is locked for that particular user
            if (!seatBookingLockProvider.validateLock(booking.getShow(), seat, user)) {
                throw new BadRequestException();
            }
        }
        booking.confirmBooking();
    }


}
