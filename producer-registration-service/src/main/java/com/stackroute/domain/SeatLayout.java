package com.stackroute.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

//                     domain Class for Seat Layout of Theatre Screens

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Seat Layout")
public class SeatLayout {
    @ApiModelProperty(notes = "Seat Number")
    @NotNull
    @Positive
    String seatNumber;
}
