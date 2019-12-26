
package com.stackroute.domain;

import lombok.*;
import org.neo4j.ogm.annotation.*;
import org.springframework.stereotype.Component;

//Relationship entity class for LIKES relationship
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@RelationshipEntity(type = "LIKES")
public class Likes {

    @Id @GeneratedValue
    private Long relId;


    @StartNode
    private User user;
    @EndNode
    private Movie movie;

}






