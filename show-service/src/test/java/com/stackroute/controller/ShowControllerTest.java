//package com.stackroute.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.stackroute.domain.Show;
//import com.stackroute.domain.Theatre;
//import com.stackroute.domain.Timing;
//import com.stackroute.service.ShowService;
//import com.stackroute.domain.Movie;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest
//public class ShowControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    Show show;
//    Movie movie;
//
//    @MockBean
//    private ShowService showService;
//
//    @InjectMocks
//    private ShowController showController;
//
//    private List<Show> showList =null;
//
//    private List<Movie> movieList =null;
//
//    @Before
//    public void setUp(){
//
//        MockitoAnnotations.initMocks(this);
//
//        show=new Show();
//        show.setCityName("Banglore");
//        movie =new Movie();
//        movie.setMovieId(1234566666);
//        movie.setMovieTitle("Dangal");
//
//        Theatre theatre=new Theatre();
//        theatre.setTheatreId(1234);
//        theatre.setTheatreName("PVR Forum");
//        List<Theatre> theatreList=new ArrayList<>();
//        theatreList.add(theatre);
//
//        movie.setTheatres(theatreList);
//
//        List<Movie> movieList =new ArrayList<>();
//        movieList.add(movie);
//        show.setMovies(movieList);
//
//        showList = new ArrayList();
//        showList.add(show);
//    }
//
//    private static String asJsonString(final Object obj)
//    {
//        try{
//            return new ObjectMapper().writeValueAsString(obj);
//
//        }catch(Exception e){
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    public void testSaveShow() throws Exception {
//        when(showService.addShow((Show)any())).thenReturn(show);
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/show")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(show)))
//                .andExpect(MockMvcResultMatchers.status().isCreated())
//                .andDo(MockMvcResultHandlers.print());
//    }
//
//    @Test
//    public void testGetAllShows() throws Exception{
//        when(showService.getAllShows()).thenReturn(showList);
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/show")
//                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(show)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print());
//    }
//
//    @Test
//    public void testGetMoviesByCityName() throws Exception{
//        when(showService.getMoviesByCityName("Banglore")).thenReturn(show.getMovies());
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/show/Banglore")
//                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print());
//    }
//
//    @Test
//    public void testUpdate() throws Exception{
//
//
//        Timing timing=new Timing();
//       String date= "2018-11-12 09:45:00";
//        timing.setShowTime(date);
//        timing.setShowId("123");
//        List<Timing> timings=new ArrayList<>();
//        timings.add(timing);
//        show.getMovies().get(0).getTheatres().get(0).setTimings(timings);
//        //Show show1=show;
//        showList.remove(0);
//        showList.add(show);
//
//        when(showService.updateShow("Banglore",1234566666,show.getMovies().get(0).getTheatres().get(0))).thenReturn(showList);
//        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/show/1234")
//                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(show)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print());
//    }
//
//
//
//
//
//}
