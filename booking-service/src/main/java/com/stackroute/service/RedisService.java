package com.stackroute.service;

import com.stackroute.domain.BookingArena;
import com.stackroute.domain.BookingDetails;
import com.stackroute.domain.SeatLayout;
import com.stackroute.exception.SeatAlreadyBookedException;

import java.util.List;

public interface RedisService {

    public BookingArena saveBookingArena (BookingArena bookingArena);
    public BookingArena update (String email, String showId, List<SeatLayout> seats, int bookingId, String type,int price) throws SeatAlreadyBookedException;
    public BookingArena deleteArena (String showId);
    public List<BookingArena> getAll ();
    public BookingArena getBookingArenaById(String showId);
    public BookingArena freeSeats(String email, String showId, List<SeatLayout> seats, int bookingId, String type,int totalPrice);
    public BookingDetails blockSeats(String email, String showId, List<SeatLayout> seats, int bookingId, String type, int totalPrice);
}
