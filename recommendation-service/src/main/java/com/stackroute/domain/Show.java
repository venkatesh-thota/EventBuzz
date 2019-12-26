package com.stackroute.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;


import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Component
public class Show {

    @Id
    private String cityName;
    private List<Movie> movies;
}