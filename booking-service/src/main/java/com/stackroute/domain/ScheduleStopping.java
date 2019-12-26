package com.stackroute.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ScheduleStopping implements Serializable {
    private String city;
    private String movie;
    private int movieId;
    private String showId;
    private int theatreId;
    private String showTiming;
    private String message;
}