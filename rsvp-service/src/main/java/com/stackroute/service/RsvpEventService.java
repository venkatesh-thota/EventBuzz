package com.stackroute.service;

import com.stackroute.domain.Event;
import com.stackroute.domain.RsvpEventProducer;
import com.stackroute.exceptions.*;

import java.util.List;

public interface RsvpEventService {
    public RsvpEventProducer getProducerByEmail(String email) throws ProducerNotFoundException;

    public RsvpEventProducer addProducer(RsvpEventProducer rsvpEventProducer) throws ProducerAlreadyExistException, ProducerNotFoundException;

    public List<RsvpEventProducer> getAllProducers();

    public boolean deleteProducer(String email) throws ProducerNotFoundException;

    public RsvpEventProducer addNewEvent(String email, Event events) throws ProducerNotFoundException, EventAlreadyExistException;

    public RsvpEventProducer deleteEvent(String email, int id) throws ProducerNotFoundException, EventNotFoundException;

    public Event getEventById(String email,int id) throws ProducerNotFoundException,EventNotFoundException;

    public RsvpEventProducer updateProducer(String email,RsvpEventProducer rsvpEventProducer) throws ProducerAlreadyExistException, ProducerNotFoundException;
}