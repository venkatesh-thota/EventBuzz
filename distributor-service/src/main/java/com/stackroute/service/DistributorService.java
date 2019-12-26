package com.stackroute.service;

import com.stackroute.domain.City;
import com.stackroute.domain.Distributor;
import com.stackroute.domain.Movie;
import com.stackroute.exceptions.*;

import java.util.List;

public interface DistributorService {
    public Distributor getDistributorByEmail(String email) throws DistributorNotFoundException;
    public Distributor addDistributor(Distributor distributor) throws DistributorAlreadyExistException,DistributorNotFoundException;
    public List<Distributor> getAllDistributors();
    public boolean deleteDistributor(String email) throws DistributorNotFoundException;
    public Distributor addNewCity(String email, City city) throws DistributorNotFoundException, CityAlreadyExistException;
    public Distributor deleteCity(String email, String cityName) throws DistributorNotFoundException, CityNotFoundException;
    public Distributor addNewMovie(String email, String cityName, Movie movie) throws DistributorNotFoundException, CityNotFoundException, MovieAlreadyExistException;
    public Distributor deleteMovie(String email, String cityName,int movieId) throws DistributorNotFoundException, CityNotFoundException,MovieNotFoundException;
    public List<Movie> getAllMoviesByCity(String cityName);
}
