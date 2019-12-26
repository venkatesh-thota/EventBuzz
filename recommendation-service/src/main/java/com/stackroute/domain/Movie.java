package com.stackroute.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.neo4j.ogm.annotation.*;

import java.util.List;

//Node entity class for movie node
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@NodeEntity
public class Movie {



    @Id
    @GeneratedValue
    private Long id;
    @Property
    private Integer movieId;
    @Property
    private String movieTitle;
    @Property
    private Long yearOfRelease;
    @JsonIgnoreProperties
    private String posterUrl;
    @JsonIgnoreProperties
    private List<Integer> ratingArray;
    @JsonIgnoreProperties
    private double averageRating;
    @Property
    private String language;
    @JsonIgnoreProperties
    private String certificate;
    @Property
    private String city;
    @Property
    private String[] genre;
    @JsonIgnoreProperties
    private List<Theatre> theatres;
    @Property
    private String director;
    @Property
    private String[] cast;


}
