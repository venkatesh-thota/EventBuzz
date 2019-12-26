package com.stackroute.service;

import com.stackroute.domain.Movie;
import com.stackroute.domain.ScheduleStopping;
import com.stackroute.domain.Show;
import com.stackroute.domain.Theatre;
import com.stackroute.exceptions.ShowAlreadyExistsException;
import com.stackroute.exceptions.ShowNotFoundException;

import java.util.List;

public interface ShowService {

    public Show addShow(Show show) throws ShowAlreadyExistsException;

    public List<Show> getAllShows();

    public List<Show> updateShow(String cityName, int movieId, Theatre theatre) throws ShowNotFoundException;

    public List<Show> delteShow(String cityName, int movieId,int theatreId) throws ShowNotFoundException;

    public List<Movie> getMoviesByCityName(String cityName) throws ShowNotFoundException;

}
