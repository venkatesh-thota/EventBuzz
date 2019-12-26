package com.stackroute.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.validation.constraints.Email;
import java.util.List;

//                                      domain class for Producer of Theatres

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Producer")
public class Producer {
    @Id
    @ApiModelProperty(notes = "Theatre Operator Email")
    @Email
    String email;

    @ApiModelProperty(notes = "Theatre")
    List<Theatre> theatres;

}
