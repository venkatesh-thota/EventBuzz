package com.stackroute.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.domain.*;
import com.stackroute.exceptions.ProducerAlreadyExistsException;
import com.stackroute.exceptions.ProducerNotFoundException;
import com.stackroute.exceptions.TheatreNotFoundException;
import com.stackroute.services.ProducerServiceImpl;
import org.junit.After;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@WebMvcTest
public class ProducerRegistrationServiceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private Theatre theatre;
    private Producer producer;
    private Category category;
    private ScreenLayout screenLayout;
    private SeatLayout seatLayout;

    @MockBean
    private ProducerServiceImpl producerServiceImpl;

    @InjectMocks
    private ProducerRegistrationServiceController producerRegistrationServiceController;

    private List<Theatre> listTheatre=null;
    private List<Producer> listProducer=null;
    private String email ="abcfd@gmail.com";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(producerRegistrationServiceController).build();
        producer = new Producer();
        theatre  = new Theatre();
        category = new Category();
        screenLayout = new ScreenLayout();
        seatLayout = new SeatLayout();


        List<Producer> listProducer = new ArrayList<>();
        List<Theatre> listTheatre = new ArrayList<>();
        List<Category> categoryList = new ArrayList<>();
        List<SeatLayout> seatLayoutList = new ArrayList<>();

        seatLayout.setSeatNumber("A1");
        seatLayoutList.add(seatLayout);

        category.setType("GOLD CLASS");
        category.setNoOfColumns(3);
        category.setNoOfRows(3);
        category.setSeatLayout(seatLayoutList);

        categoryList.add(category);
        screenLayout.setCategory(categoryList);

        theatre.setTheatreId(349);
        theatre.setTheatreName("PVR CINEMAS");
        theatre.setTheatreAddress("Forum Mall, Koramangala, Bangalore, Karnataka , pin:560065");
        theatre.setTheatreCity("BANGALORE");
        theatre.setScreenLayout(screenLayout);

        listTheatre.add(theatre);
        producer.setEmail("abcfd@gmail.com");
        producer.setTheatres(listTheatre);

        listProducer.add(producer);
    }

    @After
    public void tearDown() throws Exception {
        // After test method
    }

    @Test
    public void saveProducer() throws Exception {
        when(producerServiceImpl.addProducer(producer)).thenReturn(producer);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/producer")
               .contentType(MediaType.APPLICATION_JSON).content(asJsonString(producer)))
               .andExpect(MockMvcResultMatchers.status().isCreated())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void saveProducerFailure() throws Exception {
        when(producerServiceImpl.addProducer(producer)).thenThrow(ProducerAlreadyExistsException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/producer")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(producer)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getAllProducers() throws Exception{
        when(producerServiceImpl.getAllProducers()).thenReturn(listProducer);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/producer")
             .contentType(MediaType.APPLICATION_JSON).content(asJsonString(producer)))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteProducerFailure() throws Exception{
        when(producerServiceImpl.deleteProducer(email)).thenThrow(ProducerNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/producer/abcfd@gmail.com/")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(producer)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteProducer() throws Exception{
        when(producerServiceImpl.deleteProducer(email)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/producer/abcfd@gmail.com/")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(producer)))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void addNewTheatre() throws Exception{
        when(producerServiceImpl.addNewTheatre(email,theatre)).thenReturn(producer);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/addTheatre/abcfd@gmail.com/")
               .contentType(MediaType.APPLICATION_JSON).content(asJsonString(producer)))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getTheatreListOfProducer() throws Exception{
        when(producerServiceImpl.getTheatreByProducerEmail(email)).thenReturn(listTheatre);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/producer/abcfd@gmail.com/")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(listTheatre)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getTheatreListOfProducerFailure() throws Exception{
        when(producerServiceImpl.getTheatreByProducerEmail(email)).thenThrow(ProducerNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/producer/abcfd@gmail.com/")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(listTheatre)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteTheatre() throws Exception{
        when(producerServiceImpl.deleteTheatre(email,"PVR+CINEMAS")).thenReturn(producer);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/deleteTheatre/abcfd@gmail.com/PVR+CINEMAS")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(producer)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void deleteTheatreFailure() throws Exception{
        when(producerServiceImpl.deleteTheatre(email,"PVR+CINEMAS")).thenThrow(TheatreNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/deleteTheatre/abcfd@gmail.com/PVR+CINEMAS")
               .contentType(MediaType.APPLICATION_JSON).content(asJsonString(theatre)))
               .andExpect(MockMvcResultMatchers.status().isNotFound())
               .andDo(MockMvcResultHandlers.print());
    }

   private static String asJsonString(final Object obj) {
        try{
            return new ObjectMapper().writeValueAsString(obj);

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }


}