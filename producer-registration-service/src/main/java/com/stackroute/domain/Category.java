package com.stackroute.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotNull;
import java.util.List;

//                                  domain class for category of the seat layouts

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Category")
public class Category {
    @ApiModelProperty(notes = "Type")
    @NotNull
    String type;
    @ApiModelProperty(notes = "noOfColumn")
    @NotNull
    Integer noOfColumns;
    @ApiModelProperty(notes = "noOfRows")
    @NotNull
    Integer noOfRows;
    @ApiModelProperty(notes = "SeatLayout")
    List<SeatLayout> seatLayout;
}
