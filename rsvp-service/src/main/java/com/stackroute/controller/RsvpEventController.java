package com.stackroute.controller;


import com.stackroute.domain.RsvpEventProducer;
import com.stackroute.domain.Event;

import com.stackroute.exceptions.ProducerAlreadyExistException;
import com.stackroute.exceptions.ProducerNotFoundException;
import com.stackroute.service.RsvpEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1")
public class RsvpEventController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private RsvpEventService rsvpEventService;
    private KafkaTemplate<String, RsvpEventProducer> kafkaTemplate;


    private static final String TOPIC="kafkarsvpjson";

    @Autowired
    public RsvpEventController(RsvpEventService rsvpEventService,KafkaTemplate<String, RsvpEventProducer> kafkaTemplate) {
        this.rsvpEventService = rsvpEventService;
        this.kafkaTemplate=kafkaTemplate;
    }

    /*.....................................Add A Producer..........................................*/

    @PostMapping("rsvpProducer")
    public ResponseEntity<?> saveProducer(@Valid @RequestBody RsvpEventProducer rsvpEventProducer) {
        ResponseEntity responseEntity;
        try {
            RsvpEventProducer savedProducer = rsvpEventService.addProducer(rsvpEventProducer);
            responseEntity = new ResponseEntity<RsvpEventProducer>(savedProducer, HttpStatus.OK);
        } catch (ProducerAlreadyExistException e) {
            responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (Exception ex) {
            responseEntity = new ResponseEntity<String>(ex.getMessage(), HttpStatus.CONFLICT);
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }

        return responseEntity;
    }

    /*.................................Get All Producers................................................*/


    @GetMapping("rsvpProducer")
    public ResponseEntity<?> getAllProducers() {
        List<RsvpEventProducer> rsvpEventProducers;
        rsvpEventProducers = rsvpEventService.getAllProducers();
        ResponseEntity responseEntity = new ResponseEntity<List<RsvpEventProducer>>(rsvpEventProducers, HttpStatus.OK);
        return responseEntity;
    }

    /*............................................Get a specific Producer..................................*/


    @GetMapping(value = "rsvpProducer/{email}")
    public ResponseEntity<?> searchProducer(@PathVariable String email) {
        ResponseEntity responseEntity;
        try {
            RsvpEventProducer rsvpEventProducer = rsvpEventService.getProducerByEmail(email);
            responseEntity = new ResponseEntity<RsvpEventProducer>(rsvpEventProducer, HttpStatus.OK);
        } catch (ProducerNotFoundException e) {
            responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (Exception ex) {
            responseEntity = new ResponseEntity<String>(ex.getMessage(), HttpStatus.CONFLICT);
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        return responseEntity;
    }


    /*..............................Delete a producer............................................*/


    @DeleteMapping(value = "rsvpProducer/{email}")
    public ResponseEntity<?> deleteProducer(@PathVariable String email) {
        ResponseEntity responseEntity;
        try {
            Boolean bool = rsvpEventService.deleteProducer(email);
            responseEntity = new ResponseEntity<Boolean>(bool, HttpStatus.OK);
        } catch (ProducerNotFoundException e) {
            responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (Exception ex) {
            responseEntity = new ResponseEntity<String>(ex.getMessage(), HttpStatus.CONFLICT);
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        return responseEntity;
    }

    /*..............................................Add New Event............................................*/


    @PutMapping(value = "rsvpProducer/event/{email}")
    public ResponseEntity<?> addNewEvent(@PathVariable String email, @Valid @RequestBody Event event) {
        ResponseEntity responseEntity;
        try {
            RsvpEventProducer rsvpEventProducer = rsvpEventService.addNewEvent(email, event);
            kafkaTemplate.send(TOPIC,rsvpEventProducer);
            responseEntity = new ResponseEntity<RsvpEventProducer>(rsvpEventProducer, HttpStatus.OK);

        } catch (ProducerNotFoundException e) {
            responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (Exception ex) {
            responseEntity = new ResponseEntity<String>(ex.getMessage(), HttpStatus.CONFLICT);
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }

        return responseEntity;

    }



    /*..............................................Update Producer Event...........................................*/


    @PutMapping(value = "rsvpProducer/{email}")
    public ResponseEntity<?> updateProducer(@PathVariable String email, @Valid @RequestBody RsvpEventProducer rsvpEventProducer1) {
        ResponseEntity responseEntity;
        try {
            RsvpEventProducer rsvpEventProducer = rsvpEventService.updateProducer(email,rsvpEventProducer1);
            responseEntity = new ResponseEntity<RsvpEventProducer>(rsvpEventProducer, HttpStatus.OK);

        } catch (ProducerNotFoundException e) {
            responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (Exception ex) {
            responseEntity = new ResponseEntity<String>(ex.getMessage(), HttpStatus.CONFLICT);
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }

        return responseEntity;

    }

    /*..........................................Delete an Event.............................................*/


    @DeleteMapping(value = "rsvpProducer/{email}/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable String email, @PathVariable int id) {
        ResponseEntity responseEntity;
        try {
            RsvpEventProducer rsvpEventProducer = rsvpEventService.deleteEvent(email, id);
            responseEntity = new ResponseEntity<RsvpEventProducer>(rsvpEventProducer, HttpStatus.OK);
        } catch (ProducerNotFoundException e) {
            responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (Exception ex) {
            responseEntity = new ResponseEntity<String>(ex.getMessage(), HttpStatus.CONFLICT);
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        return responseEntity;

    }


    /*.......................................Get events by producerEmail..............................................*/

    @GetMapping(value = "rsvpProducer/event/{email}/{id}")
    public ResponseEntity<?> getEventById(@PathVariable String email, @PathVariable int id) {
        ResponseEntity responseEntity;
        try {

            Event event;
            event = rsvpEventService.getEventById(email,id);
            responseEntity = new ResponseEntity<Event>(event, HttpStatus.OK);
            return responseEntity;
        } catch (Exception ex) {
            responseEntity = new ResponseEntity<String>(ex.getMessage(), HttpStatus.CONFLICT);
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        return responseEntity;
    }





}

