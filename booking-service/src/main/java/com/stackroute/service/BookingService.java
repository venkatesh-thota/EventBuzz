package com.stackroute.service;

import com.stackroute.domain.Booking;
import com.stackroute.domain.BookingArena;

import java.util.List;

public interface BookingService {
    public Booking add(Booking booking);
    public Booking update(String cityName, int movieId, Booking booking);
    public List<Booking> getAll();
}
