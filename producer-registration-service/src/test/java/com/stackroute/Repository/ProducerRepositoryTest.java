package com.stackroute.Repository;

import com.stackroute.domain.*;
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
public class ProducerRepositoryTest {
    @Autowired
    ProducerRepository producerRepository;

    Producer producer;
    Producer producer1;
    Theatre theatre;
    Category category;
    ScreenLayout screenLayout;
    SeatLayout seatLayout;

    private List<Theatre> listTheatre=null;
    private String email ="abcfd@gmail.com";

    @Before
    public void setUp() throws Exception {
        producer = new Producer();
        producer1 = new Producer();
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
        producerRepository.deleteAll();
    }

    @Test
    public void findByemail() {
    Producer producer = new Producer("abcfd@gmail.com",listTheatre);
       producerRepository.save(producer);
       Producer producerFound = producerRepository.findByemail("abcfd@gmail.com");
       Assert.assertEquals("abcfd@gmail.com",producerFound.getEmail());
    }

    @Test
    public void testSaveProducer(){
      producerRepository.save(producer);
       Producer fetchMovie = producerRepository.findByemail(email);
       Assert.assertEquals("abcfd@gmail.com",fetchMovie.getEmail());

    }

   @Test
   public void testSaveProducerFailure(){
       producerRepository.save(producer);
       Producer fetchMovie = producerRepository.findByemail(email);
       Assert.assertNotEquals("abcd@gmail.com",fetchMovie.getEmail());
    }

   @Test
    public void testGetAllProducer(){
       producerRepository.save(producer);
       Iterable<Producer> list1 = producerRepository.findAll();
       List<Producer> list = new ArrayList<Producer>();
       for (Producer item : list1) {
           list.add(item);
       }
       Assert.assertEquals("abcfd@gmail.com",list.get(0).getEmail());

    }

   @Test
   public void testGetAllProducerFailiure(){
       producerRepository.save(producer);
       Iterable<Producer> list1 = producerRepository.findAll();
       List<Producer> list = new ArrayList<Producer>();
       for (Producer item : list1) {
           list.add(item);
       }
       Assert.assertNotEquals("abcd@gmail.com",list.get(0).getEmail());
   }


}