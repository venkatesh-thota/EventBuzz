package com.stackroute.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Movie {
    private Integer movieId;
    private String movieTitle;
    private String yearOfRelease;
    private String posterUrl;
    private Integer[] ratingArray;
    private Integer averageRating;
    private String language;
    private String certificate;
    private String director;
    private String[] cast;
    private String[] genre;
    private String city;

    @Override
    public String toString() {
        return "Movie{" +
                "movieId='" + movieId + '\'' +
                ", movieTitle='" + movieTitle + '\'' +
                ", yearOfRelease='" + yearOfRelease + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                ", ratingArray='" + Arrays.toString(ratingArray) + '\'' +
                ", averageRating='" + averageRating + '\'' +
                ", language='" + language + '\'' +
                ", certificate='" + certificate + '\'' +
                ", director='" + director + '\'' +
                ", city='" + city + '\'' +
                ", cast='" + Arrays.toString(cast) + '\'' +
                ", genre='" + Arrays.toString(genre) + '\'' +
                '}';
    }
}

