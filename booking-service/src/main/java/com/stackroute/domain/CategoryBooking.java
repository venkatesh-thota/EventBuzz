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
public class CategoryBooking {
    @Id
    private String type;
    private int price;
    private int noOfColums;
    private int noOfRows;
    private List<SeatLayout> seatLayoutList;
}
