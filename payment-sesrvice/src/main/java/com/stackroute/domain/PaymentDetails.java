package com.stackroute.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document
public class PaymentDetails implements Serializable {

    @Id
    private String paymentId;

    private boolean status;
    private int bookingId;

    private String email;
    private int amount;


    private int movieId;
    private String movieTitle;

    private int TheatreId;
    private String TheatreName;

    private String showTime;
    private String showId;


}
