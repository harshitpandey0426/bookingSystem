package com.lld.system.design.bookingmyshow.Scenarios;

import com.lld.system.design.bookingmyshow.Utilities.Context;
import com.lld.system.design.bookingmyshow.Utilities.SeatBookingLockStrategy;
import com.lld.system.design.bookingmyshow.api.*;
import com.lld.system.design.bookingmyshow.Utilities.InMemorySeatBookingLockStrategy;
import com.lld.system.design.bookingmyshow.model.City;
import com.lld.system.design.bookingmyshow.service.*;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class BaseTest {

    protected BookingController bookingController;
    protected ShowController showController;
    protected TheatreController theatreController;
    protected MovieController movieController;
    protected PaymentsController paymentsController;

    protected void setupControllers(int lockTimeout, int allowedRetries) {
        final SeatBookingLockStrategy seatLockProvider = new InMemorySeatBookingLockStrategy(lockTimeout);
        Context context = new Context(seatLockProvider);
        final BookingService bookingService = new BookingService(seatLockProvider);
        final MovieService movieService = new MovieService();
        final ShowService showService = new ShowService();
        final TheatreService theatreService = new TheatreService();
        final SeatAvailabilityService seatAvailabilityService
                = new SeatAvailabilityService(bookingService, seatLockProvider);
        final PaymentsService paymentsService = new PaymentsService(allowedRetries, seatLockProvider);

        bookingController = new BookingController(showService, bookingService, theatreService);
        showController = new ShowController(seatAvailabilityService, showService, theatreService, movieService);
        theatreController = new TheatreController(theatreService);
        movieController = new MovieController(movieService);
        paymentsController = new PaymentsController(paymentsService, bookingService);
    }


    protected void validateSeatsList(List<String> currentlyAvailableSeats, List<String> allSeatsInScreen, List<String> excludedSeats) {
        for (String includedSeat: allSeatsInScreen) {
            if (!excludedSeats.contains(includedSeat)) {
                Assert.assertTrue(currentlyAvailableSeats.contains(includedSeat));
            }
        }
        for (String excludedSeat: excludedSeats) {
            Assert.assertFalse(currentlyAvailableSeats.contains(excludedSeat));
        }
    }

    protected List<String> createSeats(TheatreController theatreController, String screen, int numRows, int numSeatsInRow) {
        List<String> seats = new ArrayList<>();
        for (int row = 0; row < numRows; row++) {
            for (int seatNo = 0; seatNo < numSeatsInRow; seatNo++) {
                String seat = theatreController.createSeatInScreen(row, seatNo, screen);
                seats.add(seat);
            }
        }
        return seats;
    }

    protected String setupScreen() {
        final String theatre = theatreController.createTheatre("Theatre 1", City.Bangalore);
        return theatreController.createScreenInTheatre("Screen 1", theatre);
    }
}
