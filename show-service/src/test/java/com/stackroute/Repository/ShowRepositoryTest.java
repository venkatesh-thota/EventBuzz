package com.stackroute.Repository;

import com.stackroute.domain.Movie;
import com.stackroute.domain.Show;
import com.stackroute.domain.Theatre;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ShowRepositoryTest {

    @Autowired
    ShowRepository showRepository;

    Show show;

    @Before
    public void setUp(){
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
    public void testSaveShow(){

        showRepository.save(show);
        Show show1=showRepository.findById(show.getCityName()).get();
        Assert.assertEquals(1234,show1.getMovies().get(0).getTheatres().get(0).getTheatreId());
    }

    @Test
    public void testGetAllShows(){

        Show show1=new Show("Chennai",null);
        showRepository.save(show);
        showRepository.save(show1);
        List<Show> showList=showRepository.findAll();
        Assert.assertEquals(2,showList.size());
    }

    @Test
    public void testShowBYId(){
        showRepository.save(show);
        Assert.assertEquals("Banglore",showRepository.findById("Banglore").get().getCityName());
    }

    @Test
    public void testDeleteById(){
        Show show1=new Show("Chennai",null);
        showRepository.save(show);
        showRepository.save(show1);
        showRepository.deleteById("Chennai");
        Assert.assertEquals(1,showRepository.findAll().size());

    }


}
