package com.lld.system.design.bookingmyshow.model;

import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Theatre {

    private final String id;
    private final String name;
    private final List<Screen> screens;
    private final City city;

    public Theatre(@NonNull final String id, @NonNull final String name,City city) {
        this.id = id;
        this.name = name;
        this.screens = new ArrayList<>();
        this.city=city;
    }

    public void addScreen(@NonNull final  Screen screen) {
        screens.add(screen);
    }
}