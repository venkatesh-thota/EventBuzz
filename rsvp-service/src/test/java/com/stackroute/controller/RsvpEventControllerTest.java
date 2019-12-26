package com.stackroute.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.domain.Event;
import com.stackroute.domain.RsvpEventProducer;
import com.stackroute.service.RsvpEventServiceImpl;
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
public class RsvpEventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RsvpEventServiceImpl rsvpEventService;
    @InjectMocks
    private RsvpEventController rsvpEventController;

    Event event;
    RsvpEventProducer rsvpEventProducer;
    List<RsvpEventProducer> producerList=new ArrayList<>();
    List<RsvpEventProducer> producers;
    List<Event> events=new ArrayList<>();

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        event=new Event();
        String[] invitations = new String[]{"akash@gmail.com","akshay@gmail.com"};
        rsvpEventProducer=new RsvpEventProducer();
        event.setInvitations(invitations);
        event.setInvitationMessage("you are heartiely invited");

        event.setEventName("Seminar");

        List<Event> eventList=new ArrayList<>();
        eventList.add(event);
        rsvpEventProducer.setEmail("ashu@gmail.com");
        rsvpEventProducer.setEvents(eventList);
        producerList.add(rsvpEventProducer);
    }

    @Test
    public void getAllProducers() throws Exception{
        when(rsvpEventService.getAllProducers()).thenReturn(producerList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/producer")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(rsvpEventProducer)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void searchProducer() throws Exception{
        when(rsvpEventService.getProducerByEmail(rsvpEventProducer.getEmail())).thenReturn(rsvpEventProducer);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/producer/ashu@gmail.com")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(rsvpEventProducer)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteProducer() throws Exception{

        when(rsvpEventService.deleteProducer(rsvpEventProducer.getEmail())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/producer/ashu@gmail.com")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(rsvpEventProducer)))
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