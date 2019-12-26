package com.stackroute.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//                                         domain class for Theatre

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Theatre")
public class Theatre {
    @ApiModelProperty(notes = "Theatre Id")
    @Id
    Integer theatreId;
    @ApiModelProperty(notes = "Theatre Name")
    String theatreName;
    @ApiModelProperty(notes = "Theatre City")
    String theatreCity;
    @ApiModelProperty(notes = "Theatre Address")
    String theatreAddress;
    @ApiModelProperty(notes = "Theatre ScreenLayout")
    ScreenLayout screenLayout;

}
