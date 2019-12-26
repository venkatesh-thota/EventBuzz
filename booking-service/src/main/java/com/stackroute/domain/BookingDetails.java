package com.stackroute.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetails {

    @Id
    private int bookingId;
    private String email;
    private String movieTitle;
    private int movieId;
    private String theatreName;
    private int theatreId;
    private String showTime;
    private String showId;
    private int bookingStatus ;
    private boolean paymentStatus ;
    private List<SeatLayout> seats;
    private int totalPrice;
}

