package com.stackroute.service;

import com.stackroute.Repository.ShowRepository;
import com.stackroute.domain.Movie;
import com.stackroute.domain.Show;
import com.stackroute.domain.Theatre;
import com.stackroute.exceptions.ShowAlreadyExistsException;
import com.stackroute.exceptions.ShowNotFoundException;
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
import static org.mockito.Mockito.when;

public class ShowServiceTest {

    Show show;

    @Mock
    ShowRepository showRepository;

    @InjectMocks
    ShowServiceImpl showService;
    List<Show> showList=null;

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);

        show=new Show();
        show.setCityName("Banglore");
        Movie movie =new Movie();
        movie.setMovieId(1234566666);
        movie.setMovieTitle("Dangal");

        Theatre theatre=new Theatre();
        theatre.setTheatreId(1234);
        theatre.setTheatreName("PVR Forum");
        List<Theatre> theatreList=new ArrayList<>();
        theatreList.add(theatre);

        movie.setTheatres(theatreList);

        List<Movie> movieList =new ArrayList<>();
        movieList.add(movie);
        show.setMovies(movieList);
    }

    @After
    public void tearDown(){
        showRepository.deleteAll();
    }

    @Test
    public void testSaveShow() throws ShowAlreadyExistsException {
        when(showRepository.save((Show)any())).thenReturn(show);
        Assert.assertEquals("Banglore",showService.addShow(show).getCityName());
    }

    @Test
    public void testGetAllShows() throws ShowAlreadyExistsException{
        showService.addShow(show);
        when(showRepository.findAll()).thenReturn(showList);
        Assert.assertEquals(showList,showService.getAllShows());
    }

    @Test
    public void testUpdateShow() throws ShowAlreadyExistsException{
        when(showRepository.save((Show)any())).thenReturn(show);
        Show savedShow=showService.addShow(show);
        savedShow.setMovies(null);
        Assert.assertEquals(null,savedShow.getMovies());

    }

    @Test(expected = ShowNotFoundException.class)
    public void testGetMovieByCityName() throws ShowAlreadyExistsException,ShowNotFoundException{
        when(showRepository.save((Show)any())).thenReturn(show);
        Show savedShow=showService.addShow(show);
        when(showRepository.getShowByCityName((String)any())).thenReturn(show);
        Assert.assertEquals(savedShow,showService.getMoviesByCityName(show.getCityName()));
    }

}
