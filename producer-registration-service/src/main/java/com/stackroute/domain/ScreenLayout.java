package com.stackroute.domain;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;
import java.util.List;

//                                    domain class of Screen Layout of Theatres

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Screen Layout")
public class ScreenLayout implements Serializable {
    @ApiModelProperty(notes = "Category")
    private List<Category> category;
}
