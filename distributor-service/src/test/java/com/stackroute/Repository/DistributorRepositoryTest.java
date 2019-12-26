package com.stackroute.Repository;

import com.stackroute.domain.City;
import com.stackroute.domain.Distributor;
import com.stackroute.domain.Movie;
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
public class DistributorRepositoryTest {

    @Autowired
    DistributorRepository distributorRepository;

    Movie movie;
    City city;
    Distributor distributor;

    @Before
    public void setUp() throws Exception {
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
        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie);
        city.setCityName("Bangalore");
        city.setMovies(movieList);
        List<City> cityList=new ArrayList<>();
        cityList.add(city);
        distributor.setEmail("arshadshibli@gmail.com");
        distributor.setCities(cityList);

    }

    @After
    public void tearDown() throws Exception {
        distributorRepository.deleteAll();
    }

    @Test
    public void findByemail() {

    }

    @Test
    public void testSaveDistributor()
    {
        Distributor savedDistributor=distributorRepository.save(distributor);
        Assert.assertEquals(savedDistributor,distributor);
    }
    @Test
    public void testFindAll()
    {
        distributorRepository.save(distributor);
        List<Distributor> distributorList=(List<Distributor>) distributorRepository.findAll();
        Assert.assertEquals(distributorList.get(0).getEmail(),distributor.getEmail());
    }
    @Test
    public void testFindByEmail()
    {
        distributorRepository.save(distributor);
        Distributor getDistributor=distributorRepository.findByemail(distributor.getEmail());
        Assert.assertEquals(distributor,getDistributor);
    }
    @Test
    public void testDelete()
    {
        distributorRepository.save(distributor);
        distributorRepository.delete(distributor);
        Distributor retrieved=distributorRepository.findByemail(distributor.getEmail());
        Assert.assertNull(retrieved);

    }
}