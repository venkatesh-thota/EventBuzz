package com.stackroute.services;

import com.stackroute.domain.*;
import com.stackroute.exceptions.ProducerAlreadyExistsException;
import com.stackroute.exceptions.ProducerNotFoundException;
import com.stackroute.exceptions.TheatreNotFoundException;
import com.stackroute.Repository.ProducerRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ProducerServiceTest {
    private Theatre theatre;
    private Category category;
    private ScreenLayout screenLayout;
    private SeatLayout seatLayout;
    private Producer producer;

    //Create a mock for MovieRepository
   @Mock//test double
    ProducerRepository producerRepository;

   //Inject the mocks as dependencies into MovieServiceImpl
    @InjectMocks
    ProducerServiceImpl producerService;

    List<Producer> listProducer= null;
    List<Theatre> listTheatre= null;


    @Before
    public void setUp() throws Exception {
        //Initialising the mock object
        MockitoAnnotations.initMocks(this);
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
        //After test method
    }

    @Test
    public void getAllProducers() {
        //stubbing the mock to return specific data
        when(producerRepository.findAll()).thenReturn(listProducer);
        List<Producer> producerlist = producerService.getAllProducers();
        Assert.assertEquals(listProducer,producerlist);
    }


    @Test
    public void addProducerTestSucess() throws ProducerAlreadyExistsException {
        when(producerRepository.save((Producer) any())).thenReturn(producer);
        Producer savedProducer = producerService.addProducer(producer);
        Assert.assertEquals(producer,savedProducer);
        //verify here verifies that movieRepository save method is only called once
        verify(producerRepository,times(1)).save(producer);
    }

    @Test(expected = ProducerAlreadyExistsException.class)
    public void addProducerTestFailure() throws ProducerAlreadyExistsException {
       when(producerRepository.save((Producer) any())).thenReturn(null);
       Producer savedProducer = producerService.addProducer(producer);
       Assert.assertEquals(producer,savedProducer);
       verify(producerRepository,times(1)).save(producer);
       doThrow(new ProducerAlreadyExistsException("Movie Already Exists")).when(producerRepository).findByemail(eq("abcfd@gmail.com"));
        producerService.addProducer(producer);
    }

    @Test
    public void deleteProducer() throws ProducerNotFoundException {
      //stubbing the mock to return specific data
        when(producerRepository.findByemail("abcfd@gmail.com")).thenReturn(producer);
       doNothing().when(producerRepository).delete((Producer) any());
        Boolean bool = producerService.deleteProducer("abcfd@gmail.com");
       Assert.assertEquals(true,bool);
        //add verify
       verify(producerRepository,times(4)).findByemail("abcfd@gmail.com");

    }

    @Test
    public void getProducerByEmail() throws ProducerNotFoundException {
       //stubbing the mock to return specific data
       when(producerRepository.findByemail("abcfd@gmail.com")).thenReturn(producer);
       Producer prodFetched = producerService.getProducerByEmail("abcfd@gmail.com");
       Assert.assertEquals(prodFetched, producer);
    }


    @Test
    public void getTheatreByProducerEmail() throws  ProducerNotFoundException{
       //stubbing the mock to return specific data
        when(producerRepository.findByemail("abcfd@gmail.com")).thenReturn(producer);
        List<Theatre> theatreListFetched = producerService.getTheatreByProducerEmail("abcfd@gmail.com");
        Assert.assertEquals(theatreListFetched,producer.getTheatres());
        //add verify
    }


    @Test
    public void getTheatreByTheatreName() throws ProducerNotFoundException, TheatreNotFoundException {
        //stubbing the mock to return specific data
        when(producerRepository.findByemail("abcfd@gmail.com")).thenReturn(producer);
        List<Theatre> theatreListFetched = producer.getTheatres();
        Theatre theatreFetched = producerService.getTheatreByTheatreName("abcfd@gmail.com", "PVR CINEMAS");
        for(int i=0;i<theatreListFetched.size();i++)
        {
            if(theatreFetched.equals(theatreListFetched.get(i).getTheatreName()))
            {
                Assert.assertEquals(theatreFetched.getTheatreName(),theatreListFetched.get(i).getTheatreName());
            }
        }
    }

    @Test
    public void deleteTheatre() throws ProducerNotFoundException,TheatreNotFoundException {
        //stubbing the mock to return specific data
        when(producerRepository.findByemail("abcfd@gmail.com")).thenReturn(producer);
        when(producerRepository.save((Producer) any())).thenReturn(producer);
        Producer updatedProd= producerService.deleteTheatre("abcfd@gmail.com","PVR CINEMAS");
        Assert.assertEquals(listTheatre,null);
        //add verify
        verify(producerRepository,times(4)).findByemail("abcfd@gmail.com");
        verify(producerRepository,times(1)).save(producer);
    }

}