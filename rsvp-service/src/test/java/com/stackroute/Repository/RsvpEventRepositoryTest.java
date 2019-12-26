package com.stackroute.Repository;

import com.stackroute.domain.Event;
import com.stackroute.domain.RsvpEventProducer;
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
public class RsvpEventRepositoryTest {

    @Autowired
    RsvpEventRepository rsvpEventRepository;

    Event event;
    RsvpEventProducer rsvpEventProducer;

    @Before
    public void setUp() throws Exception {

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

    }

    @After
    public void tearDown() throws Exception {
        rsvpEventRepository.deleteAll();
    }


    @Test
    public void testSaveProducer()
    {
        RsvpEventProducer savedProducer=rsvpEventRepository.save(rsvpEventProducer);
        Assert.assertEquals(savedProducer,rsvpEventProducer);
    }
    @Test
    public void testFindAll()
    {
        rsvpEventRepository.save(rsvpEventProducer);
        List<RsvpEventProducer> producerList=(List<RsvpEventProducer>) rsvpEventRepository.findAll();
        Assert.assertEquals(producerList.get(0).getEmail(),rsvpEventProducer.getEmail());
    }
    @Test
    public void testFindByProducerEmail()
    {
        rsvpEventRepository.save(rsvpEventProducer);
        RsvpEventProducer getProducer=rsvpEventRepository.findByEmail(rsvpEventProducer.getEmail());
        Assert.assertEquals(rsvpEventProducer.toString().trim(),getProducer.toString().trim());
    }
    @Test
    public void testDelete()
    {
        rsvpEventRepository.save(rsvpEventProducer);
        rsvpEventRepository.delete(rsvpEventProducer);
        RsvpEventProducer retrieved=rsvpEventRepository.findByEmail(rsvpEventProducer.getEmail());
        Assert.assertNull(retrieved);

    }

}