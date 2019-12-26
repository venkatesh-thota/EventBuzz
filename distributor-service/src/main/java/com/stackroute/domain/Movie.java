package com.stackroute.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id

    int  movieId;
    @NotNull
    String movieTitle;
    @NotNull
    @Size(min = 4,max = 4)
    String yearOfRelease;
    @NotNull
    String posterUrl;
    List<Integer> ratingArray;
    double averageRating;
    String language;
    String certificate;
    String director;
    List<String> cast;
    List<String> genre;

}
