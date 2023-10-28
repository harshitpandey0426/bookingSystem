package com.lld.system.design.bookingmyshow.service;

import com.lld.system.design.bookingmyshow.model.Screen;
import com.lld.system.design.bookingmyshow.model.Seat;
import com.lld.system.design.bookingmyshow.model.Show;
import com.lld.system.design.bookingmyshow.model.Theatre;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TheatreService {
    private final Map<String, Theatre> theatres;
    private final Map<String, Seat> seats;
    private final Map<String, Screen> screens;
    public TheatreService(){
        theatres = new HashMap<String,Theatre>();
        seats = new HashMap<String,Seat>();
        screens = new HashMap<String,Screen>();
    }

    public Seat getSeat(@NonNull final String seatId) {
        if (!seats.containsKey(seatId)) {
//            throw new NotFoundException();
        }
        return seats.get(seatId);
    }

    public Theatre getTheatre(@NonNull final String theatreId) {
        if (!theatres.containsKey(theatreId)) {
//            throw new NotFoundException();
        }
        return theatres.get(theatreId);
    }

    public Screen getScreen(@NonNull final String screenId) {
        if (!screens.containsKey(screenId)) {
//            throw new NotFoundException();
        }
        return screens.get(screenId);
    }

    public Theatre createTheatre(final String theatreName){
        String theatreId = UUID.randomUUID().toString();
        Theatre newTheatre = new Theatre(theatreId, theatreName);
        theatres.put(theatreId, newTheatre);
        return newTheatre;
    }

    public Screen createScreenInTheatre(@NonNull final String screenName, @NonNull final Theatre theatre){
        String screenId = UUID.randomUUID().toString();
        Screen screen = new Screen(screenId, screenName, theatre);
        screens.put(screenId, screen);
        theatre.addScreen(screen);
        return screen;
    }

    public Seat createSeatInScreen(@NonNull final Integer rowNo, @NonNull final Integer seatNo, @NonNull final Screen screen) {
        String seatId = UUID.randomUUID().toString();
        Seat seat = new Seat(seatId, rowNo, seatNo);
        seats.put(seatId, seat);
        screen.addSeat(seat);
        return seat;
    }

}
