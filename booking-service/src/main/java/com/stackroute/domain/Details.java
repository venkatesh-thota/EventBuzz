package com.stackroute.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Details {

    private String email;
    private String showId;
    private String[] seats;
    private int bookingId;
    private String type;
    private int totalPrice;
}
