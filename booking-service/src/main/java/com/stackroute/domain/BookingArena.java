package com.stackroute.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@RedisHash("Arena")
public class BookingArena implements Serializable {

    @Id
    private String showId;
    private String showTime;

    private String cityName;

    private int movieId;
    private String movieTitle;

    private int theatreId;
    private String theatreName;

    private ScreenLayout screenLayout;
    private List<BookingDetails> bookingList;
}

