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
public class Movie {

    @Id
    private int movieId;

    private String movieTitle;
    private String yearOfRelease;
    private String posterUrl;
    private List<Integer> ratingArray;
    private double averageRating;
    private String language;
    private String certificate;
    private String[] genre;
    private String[] cast;
    private String director;
    private List<Theatre> theatres;

}
