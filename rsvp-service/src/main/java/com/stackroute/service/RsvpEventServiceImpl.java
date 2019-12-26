package com.stackroute.service;

import com.stackroute.domain.Event;

import com.stackroute.domain.RsvpEventProducer;
import com.stackroute.exceptions.*;
import com.stackroute.Repository.RsvpEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;

import java.util.List;

@Service
public class RsvpEventServiceImpl implements RsvpEventService {
    private RsvpEventRepository rsvpEventRepository;
    private Environment environment;
    private String producerNotFound="rsvp-service.controller.producer.notFound";
    private String eventNotFound="rsvp-service.controller.event.notFound";
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public RsvpEventServiceImpl(RsvpEventRepository rsvpEventRepository, Environment environment) {
        this.rsvpEventRepository = rsvpEventRepository;
        this.environment = environment;
    }

    public RsvpEventProducer getProducerByEmail(String email) throws ProducerNotFoundException {

        if(rsvpEventRepository.findByEmail(email)==null) {
            String msg=environment.getProperty(producerNotFound);
            throw new ProducerNotFoundException(msg);
        }

        RsvpEventProducer rsvpEventProducer=rsvpEventRepository.findByEmail(email) ;
        return rsvpEventProducer;
    }

    public RsvpEventProducer addProducer(RsvpEventProducer rsvpEventProducer) throws ProducerAlreadyExistException,ProducerNotFoundException{
        RsvpEventProducer checkProducer=rsvpEventRepository.findByEmail(rsvpEventProducer.getEmail());
        if(checkProducer!=null) {
            String msg=environment.getProperty("rsvp-service.controller.producer.alreadyExists");
            throw new ProducerAlreadyExistException(msg);
        }
        RsvpEventProducer savedProducer = rsvpEventRepository.save(rsvpEventProducer);
        return savedProducer;
    }

    public RsvpEventProducer updateProducer(String email,RsvpEventProducer rsvpEventProducer) throws ProducerAlreadyExistException,ProducerNotFoundException{
        RsvpEventProducer checkProducer=rsvpEventRepository.findByEmail(email);
        RsvpEventProducer checkProducer1=rsvpEventRepository.findByEmail(rsvpEventProducer.getEmail());
        if(checkProducer==null) {
            String msg=environment.getProperty("rsvp-service.controller.producer.notFound");
            throw new ProducerNotFoundException(msg);
        }

        if(checkProducer1==null) {
            String msg=environment.getProperty("rsvp-service.controller.producer.notFound");
            throw new ProducerNotFoundException(msg);
        }


        if(checkProducer.getEmail().equals(checkProducer1.getEmail())) {
            RsvpEventProducer savedProducer = rsvpEventRepository.save(rsvpEventProducer);
            return savedProducer;
        }
        else {
            String msg=environment.getProperty("rsvp-service.controller.producer.notFound");
            throw new ProducerNotFoundException(msg);
        }
    }


    public List<RsvpEventProducer> getAllProducers(){
        return (List<RsvpEventProducer>)rsvpEventRepository.findAll();
    }

    public boolean deleteProducer(String email) throws ProducerNotFoundException{
        if(getProducerByEmail(email)==null) {
            String msg=environment.getProperty(producerNotFound);
            throw new ProducerNotFoundException(msg);
        }
        else {
            RsvpEventProducer deletedProducer = getProducerByEmail(email);
            rsvpEventRepository.delete(deletedProducer);
            return true;
        }
    }

    public RsvpEventProducer addNewEvent(String email, Event events) throws ProducerNotFoundException, EventAlreadyExistException {
        if(getProducerByEmail(email)==null) {
            String msg=environment.getProperty(producerNotFound);
            throw new ProducerNotFoundException(msg);
        }
        else
        {
            RsvpEventProducer rsvpEventProducer=getProducerByEmail(email);
            List<Event> events1;
            events1=rsvpEventProducer.getEvents();
            for(int i=0;i<events1.size();i++)
            {
                if(events.getId()==events1.get(i).getId())
                {
                    String msg=environment.getProperty("rsvp-service.controller.event.alreadyExists");
                    throw new EventAlreadyExistException(msg);}
            }
            events1.add(events);
            rsvpEventProducer.setEvents(events1);
            rsvpEventRepository.save(rsvpEventProducer);
            return rsvpEventProducer;
        }
    }

    public RsvpEventProducer deleteEvent(String email,int id) throws ProducerNotFoundException,EventNotFoundException{
            if(getProducerByEmail(email)==null) {
            String msg=environment.getProperty(producerNotFound);
            throw new ProducerNotFoundException(msg);
        }
        else {
                RsvpEventProducer rsvpEventProducer = getProducerByEmail(email);
                List<Event> events;
                events = rsvpEventProducer.getEvents();
                for (int i = 0; i < events.size(); i++) {
                    if (id==events.get(i).getId()) {
                        events.remove(i);
                        rsvpEventProducer.setEvents(events);
                        rsvpEventRepository.save(rsvpEventProducer);
                        return rsvpEventProducer;
                    }
                }
                String msg = environment.getProperty(eventNotFound);
                throw new EventNotFoundException(msg);

        }
    }




    //this method update the invitation status
    public List<RsvpEventProducer> updateEvent(String email, Event event) throws ProducerNotFoundException,EventNotFoundException{
        if(rsvpEventRepository.findByEmail(email)==null) {
            String msg=environment.getProperty(producerNotFound);
            throw new ProducerNotFoundException(msg);
        }
        RsvpEventProducer rsvpEventProducer=rsvpEventRepository.findByEmail(email);
        int flag=0;
        for(int i = 0; i<rsvpEventProducer.getEvents().size(); i++){

            if(rsvpEventProducer.getEvents().get(i).getEventName().equals(event.getEventName())){
                flag=1;
                rsvpEventProducer.getEvents().get(i).setAttending(event.getAttending());
                rsvpEventProducer.getEvents().get(i).setAttending(event.getNotAttending());
                rsvpEventProducer.getEvents().get(i).setAttending(event.getMaybeAttending());
                break;
            }
        }
        rsvpEventRepository.save(rsvpEventProducer);
        if(flag == 0){
            String msg=environment.getProperty(producerNotFound);
            throw new ProducerNotFoundException(msg);
        }
        return (List<RsvpEventProducer>)rsvpEventRepository.findAll();
    }

    public Event getEventById(String email,int id) throws ProducerNotFoundException,EventNotFoundException{
        if(rsvpEventRepository.findByEmail(email)==null) {
            String msg=environment.getProperty(producerNotFound);
            throw new ProducerNotFoundException(msg);
        }

        RsvpEventProducer rsvpEventProducer=rsvpEventRepository.findByEmail(email);

         List<Event> eventList = rsvpEventProducer.getEvents();
         for (int i=0;i<eventList.size();i++){
             if(eventList.get(i).getId() == id ){
                 Event eventFetched =  eventList.get(i);
                 return eventFetched;
             }
         }

        String msg=environment.getProperty(eventNotFound);
        throw new EventNotFoundException(msg);
    }



}
