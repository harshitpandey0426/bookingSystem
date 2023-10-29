package com.lld.system.design.bookingmyshow.api;

import com.lld.system.design.bookingmyshow.model.Booking;
import com.lld.system.design.bookingmyshow.model.Seat;
import com.lld.system.design.bookingmyshow.model.Show;
import com.lld.system.design.bookingmyshow.service.BookingService;
import com.lld.system.design.bookingmyshow.service.ShowService;
import com.lld.system.design.bookingmyshow.service.TheatreService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookingController {
    private final ShowService showService;
    private final BookingService bookingService;
    private final TheatreService theatreService;

    public BookingController(ShowService showService, BookingService bookingService, TheatreService theatreService) {
        this.showService = showService;
        this.bookingService = bookingService;
        this.theatreService = theatreService;
    }

    public String createBooking(@NonNull final String userId, @NonNull final String showId,
                                @NonNull final List<String> seatsIds) {
        final Show show = showService.getShow(showId);
        final List<Seat> seats = getSeats(seatsIds,userId);
        Booking booking = bookingService.createBooking(userId, show, seats);
        return booking.getId();
//        return ResponseEntity.status(HttpStatus.OK).body(booking.getId());
    }
    private List<Seat> getSeats(List<String> seatsIds,String userId){
        List<Seat> seats = new ArrayList<>();
        for (String seatId : seatsIds) {
            Seat seat = theatreService.getSeat(seatId);
            seats.add(seat);
        }
        return seats;
    }
}
