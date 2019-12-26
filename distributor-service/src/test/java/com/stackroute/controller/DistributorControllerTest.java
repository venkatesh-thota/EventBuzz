package com.stackroute.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.domain.City;
import com.stackroute.domain.Distributor;
import com.stackroute.domain.Movie;
import com.stackroute.service.DistributorServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest
public class DistributorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DistributorServiceImpl distributorService;
    @InjectMocks
    private DistributorController distributorController;

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


    @Test
    public void getAllDistributors() throws Exception {
        when(distributorService.getAllDistributors()).thenReturn(distributorList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/distributor")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(distributor)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void searchDistributor() throws Exception {
        when(distributorService.getDistributorByEmail(distributor.getEmail())).thenReturn(distributor);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/distributor/arshadshibli@gmail.com")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(distributor)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteDistributor() throws Exception {
        when(distributorService.deleteDistributor(distributor.getEmail())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/distributor/arshadshibli@gmail.com")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(distributor)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}