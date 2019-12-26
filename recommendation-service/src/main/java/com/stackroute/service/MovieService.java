package com.stackroute.service;


import com.stackroute.domain.Movie;
import com.stackroute.domain.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;

//Definition of all sorting and recommendation methods to to be deployed by controller
public interface MovieService {

    public void saveLikes(String username, Integer movieId);

    public List<Movie> topThreeMovies(String city);

    public Collection<Movie> getAllMovies();

    public List<Movie> getTrendingMovieByCity(String city);

    public List<Movie> getMovieByLanguage(String language);

    public Set<Movie> getMovieByGenre(String genre);

    public Set<Movie> getMovieByCast(String cast);

    public List<Movie> getMovieByDirector(String director);

    public List<Movie> getTrendingMovieByUserCity(String city);

    public List<Movie> getMovieByUserLanguage(String language);

    public Set<Movie> getMovieByUserGenre(String genre);

    public Set<Movie> getMovieByUserCast(String cast);

    public List<Movie> getMovieByUserDirector(String director);

    public void saveMovie(List<Movie> movie);

    public void saveUser(User user);

}
