package com.stackroute.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Theatre {

    private int theatreId;
    private String theatreName;
    private String theatreCity;
    private String theatreAddress;
    private List<Timing> timings;
}
