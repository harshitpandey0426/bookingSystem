package com.lld.system.design.bookingmyshow.service;

import com.lld.system.design.bookingmyshow.model.*;
import lombok.NonNull;
import com.lld.system.design.bookingmyshow.model.Show;
import org.springframework.stereotype.Service;

import java.util.*;

public class ShowService {

    private final Map<String, Show> shows;
    Map<Movie,List<Show>>movieVsShow;

    public ShowService() {
        this.shows = new HashMap<>();
        this.movieVsShow=new HashMap<>();
    }

    public Show getShow(@NonNull final String showId) {
        if (!shows.containsKey(showId)) {
//            throw new NotFoundException();
        }
        return shows.get(showId);
    }

    public Show createShow(@NonNull final Movie movie, @NonNull final Screen screen, @NonNull final Date startTime,
                           @NonNull final Integer durationInSeconds) {
        String showId = UUID.randomUUID().toString();
        final Show show = new Show(showId, movie, screen, startTime, durationInSeconds);
        this.shows.put(showId, show);
        List<Show>allMovieShows=movieVsShow.getOrDefault(movie,new ArrayList<>());
        allMovieShows.add(show);
        movieVsShow.put(movie,allMovieShows);
        return show;
    }
    //to get particular show is on which all screen
    private List<Show> getShowsForScreen(final Screen screen) {
        final List<Show> response = new ArrayList<>();
        for (Show show : shows.values()) {
            if (show.getScreen().equals(screen)) {
                response.add(show);
            }
        }
        return response;
    }
    public List<Show> getAllShow(City city, Movie movie){
        return movieVsShow.get(movie);
    }
}
