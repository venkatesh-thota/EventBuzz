package com.stackroute.service;

import com.stackroute.domain.City;
import com.stackroute.domain.Distributor;
import com.stackroute.domain.Movie;
import com.stackroute.exceptions.DistributorAlreadyExistException;
import com.stackroute.exceptions.DistributorNotFoundException;
import com.stackroute.Repository.DistributorRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DistributorServiceTest {
    @Mock
    DistributorRepository distributorRepository;

    @InjectMocks
    DistributorServiceImpl distributorService;

    Movie movie;
    City city;
    Distributor distributor;
    List<Distributor> distributorList=new ArrayList<>();
    List<Distributor> distributors;
    List<Movie> movieList = new ArrayList<>();
    List<City> cityList=new ArrayList<>();

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        movie =new Movie();
        city=new City();
        distributor=new Distributor();
        movie.setMovieId(1);
        movie.setMovieTitle("Sarkar");
        movie.setYearOfRelease("2018");
        movie.setRatingArray(null);
        movie.setAverageRating(0);
        movie.setLanguage("Tamil");
        movie.setCertificate("U");
        movie.setGenre(null);
        movie.setCast(null);
        movie.setDirector("Arshad");
        movieList.add(movie);
        city.setCityName("Bangalore");
        city.setMovies(movieList);
        cityList.add(city);
        distributor.setEmail("arshadshibli@gmail.com");
        distributor.setCities(cityList);
        distributorList.add(distributor);

    }

    @After
    public void tearDown() throws Exception {
        distributorRepository.deleteAll();
        distributorList=null;
    }

    @Test
    public void getDistributorByEmail() throws DistributorNotFoundException {
        when(distributorRepository.findByemail((distributor.getEmail()))).thenReturn(distributor);
        Distributor retrievedDistributor = distributorService.getDistributorByEmail(distributor.getEmail());
        Assert.assertEquals(distributor,retrievedDistributor);
    }

    @Test
    public void addDistributor() throws DistributorAlreadyExistException {
        when(distributorRepository.save((Distributor) any())).thenReturn(distributor);
        Distributor savedDistributor = distributorService.addDistributor(distributor);
        Assert.assertEquals(distributor,savedDistributor);
    }

    @Test
    public void getAllDistributors() {
        when(distributorRepository.findAll()).thenReturn(distributorList);
        distributors=distributorService.getAllDistributors();
        Assert.assertEquals(distributorList,distributors);
    }
}