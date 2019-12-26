package com.stackroute.service;

import com.stackroute.domain.Event;
import com.stackroute.domain.RsvpEventProducer;
import com.stackroute.exceptions.ProducerAlreadyExistException;
import com.stackroute.exceptions.ProducerNotFoundException;
import com.stackroute.Repository.RsvpEventRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;


public class RsvpEventServiceTest {
    @Mock
    RsvpEventRepository rsvpEventRepository;

    @InjectMocks
    RsvpEventServiceImpl rsvpEventService;

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

    @After
    public void tearDown() throws Exception {
        rsvpEventRepository.deleteAll();
        producerList=null;
    }

    @Test
    public void getProducerByEmail() throws ProducerNotFoundException {
        when(rsvpEventRepository.findByEmail((rsvpEventProducer.getEmail()))).thenReturn(rsvpEventProducer);
        RsvpEventProducer retrievedProducer = rsvpEventService.getProducerByEmail(rsvpEventProducer.getEmail());
        Assert.assertEquals(rsvpEventProducer,retrievedProducer);
    }

    @Test
    public void addProducer() throws ProducerAlreadyExistException,ProducerNotFoundException {
        when(rsvpEventRepository.save((RsvpEventProducer) any())).thenReturn(rsvpEventProducer);
        RsvpEventProducer savedProducer= rsvpEventService.addProducer(rsvpEventProducer);
        Assert.assertEquals(rsvpEventProducer,savedProducer);
    }

    @Test
    public void getAllProducers() {
        when(rsvpEventRepository.findAll()).thenReturn(producerList);
        producers=rsvpEventService.getAllProducers();
        Assert.assertEquals(producerList,producers);
    }



}