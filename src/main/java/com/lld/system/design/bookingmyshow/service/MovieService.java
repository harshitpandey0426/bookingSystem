package com.lld.system.design.bookingmyshow.service;

import com.lld.system.design.bookingmyshow.model.City;
import com.lld.system.design.bookingmyshow.model.Movie;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
public class MovieService {

    final Map<String, Movie> movies;
    Map<City, List<Movie>> cityMovieMap = new HashMap<>();
    List<Movie>allMovies = new ArrayList<>();

    public MovieService() {
        this.movies = new HashMap<>();
    }
    public Movie getMovie(@NonNull final String movieId) {
        if (!movies.containsKey(movieId)) {
//            throw new NotFoundException();
        }
        return movies.get(movieId);
    }

    public Movie createMovie(final String movieName){
        String  movieId = UUID.randomUUID().toString();
        Movie movie = new Movie(movieId, movieName);
        movies.put(movieId,movie);
        return movie;
    }
}
