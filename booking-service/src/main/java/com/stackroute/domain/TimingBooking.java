package com.stackroute.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TimingBooking {
    @Id
    private String showId;
    private String showTime;
    private ScreenLayout screenLayout;
    private List<BookingDetails> bookings;

}
