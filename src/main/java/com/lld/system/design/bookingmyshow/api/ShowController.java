package com.lld.system.design.bookingmyshow.api;

import com.lld.system.design.bookingmyshow.model.Movie;
import com.lld.system.design.bookingmyshow.model.Screen;
import com.lld.system.design.bookingmyshow.model.Seat;
import com.lld.system.design.bookingmyshow.model.Show;
import com.lld.system.design.bookingmyshow.service.MovieService;
import com.lld.system.design.bookingmyshow.service.SeatAvailabilityService;
import com.lld.system.design.bookingmyshow.service.ShowService;
import com.lld.system.design.bookingmyshow.service.TheatreService;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ShowController {
    private final SeatAvailabilityService seatAvailabilityService;
    private final ShowService showService;
    private final TheatreService theatreService;
    private final MovieService movieService;

    public String createShow(@NonNull final String movieId, @NonNull final String screenId, @NonNull final Date startTime,
                             @NonNull final Integer durationInSeconds) {
        final Screen screen = theatreService.getScreen(screenId);
        final Movie movie = movieService.getMovie(movieId);
        return showService.createShow(movie, screen, startTime, durationInSeconds).getId();
    }

    public List<String> getAvailableSeats(@NonNull final String showId) {
        final Show show = showService.getShow(showId);
        final List<Seat> availableSeats = seatAvailabilityService.getAvailableSeats(show);
        List<String> seatIds = new ArrayList<>();

        for (Seat seat : availableSeats) {
            seatIds.add(seat.getId());
        }
        return seatIds;
    }
}
