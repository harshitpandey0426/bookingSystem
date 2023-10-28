package com.lld.system.design.bookingmyshow.service;

import com.lld.system.design.bookingmyshow.model.Movie;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@Service
public class MovieService {

    final Map<String, Movie> movies;
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
