package com.lld.system.design.bookingmyshow.api;

import com.lld.system.design.bookingmyshow.model.City;
import com.lld.system.design.bookingmyshow.model.Movie;
import com.lld.system.design.bookingmyshow.service.MovieService;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.List;

@AllArgsConstructor
public class MovieController {
    final private MovieService movieService;


    public String createMovie(City city, @NonNull final String movieName) {
        return movieService.createMovie(city,movieName).getId();
    }
    public List<Movie> getMoviesByCity(City city) {
        return movieService.getMoviesByCity(city);
    }
    public Movie getMovie(@NonNull final String movieId) {

        return movieService.getMovie(movieId);
    }
}
