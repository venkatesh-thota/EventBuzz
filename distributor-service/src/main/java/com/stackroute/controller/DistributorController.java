package com.stackroute.controller;

import com.stackroute.domain.City;
import com.stackroute.domain.Distributor;
import com.stackroute.domain.Movie;
import com.stackroute.exceptions.DistributorAlreadyExistException;
import com.stackroute.exceptions.DistributorNotFoundException;
import com.stackroute.service.DistributorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1")
public class DistributorController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private DistributorService distributorService;
    private KafkaTemplate<String, Distributor> kafkaTemplate;

    private static final String TOPIC = "distributorlist";

     @Autowired
    public DistributorController(DistributorService distributorService,KafkaTemplate<String, Distributor> kafkaTemplate) {
        this.distributorService = distributorService;
        this.kafkaTemplate=kafkaTemplate;

    }




    /*.....................................Add A Distributor..........................................*/

    @PostMapping("distributor")
    public ResponseEntity<?> saveDistributor(@Valid @RequestBody Distributor distributor) {
        ResponseEntity responseEntity;
        try {
            Distributor savedDistributor = distributorService.addDistributor(distributor);
            kafkaTemplate.send(TOPIC,distributor);
            responseEntity = new ResponseEntity<Distributor>(savedDistributor, HttpStatus.OK);
        } catch (DistributorAlreadyExistException e) {
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

    /*.................................Get All Distributors................................................*/


    @GetMapping("/distributor")
    public ResponseEntity<?> getAllDistributors() {
        List<Distributor> distributorsList;
        distributorsList = distributorService.getAllDistributors();
        ResponseEntity responseEntity = new ResponseEntity<List<Distributor>>(distributorsList, HttpStatus.OK);
        return responseEntity;
    }

    /*............................................Get a single Distributor..................................*/


    @GetMapping(value = "distributor/{email}")
    public ResponseEntity<?> searchDistributor(@PathVariable String email) {
        ResponseEntity responseEntity;
        try {
            Distributor distributor = distributorService.getDistributorByEmail(email);
            responseEntity = new ResponseEntity<Distributor>(distributor, HttpStatus.OK);
        } catch (DistributorNotFoundException e) {
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

    /*..............................Delete a distributor............................................*/


    @DeleteMapping(value = "distributor/{email}")
    public ResponseEntity<?> deleteDistributor(@PathVariable String email) {
        ResponseEntity responseEntity;
        try {
            Boolean bool = distributorService.deleteDistributor(email);
            responseEntity = new ResponseEntity<Boolean>(bool, HttpStatus.OK);
        } catch (DistributorNotFoundException e) {
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

    /*..............................................Add New City............................................*/


    @PutMapping(value = "distributor/{email}")
    public ResponseEntity<?> addNewCity(@PathVariable String email, @Valid @RequestBody City city) {
        ResponseEntity responseEntity;
        try {
            Distributor distributor = distributorService.addNewCity(email, city);
            kafkaTemplate.send(TOPIC,distributor);
            responseEntity = new ResponseEntity<Distributor>(distributor, HttpStatus.OK);

        } catch (DistributorNotFoundException e) {
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

    /*..........................................Delete a City.............................................*/


    @DeleteMapping(value = "distributor/{email}/{cityName}")
    public ResponseEntity<?> deleteCity(@PathVariable String email, @PathVariable String cityName) {
        ResponseEntity responseEntity;
        try {
            Distributor distributor = distributorService.deleteCity(email, cityName);
            responseEntity = new ResponseEntity<Distributor>(distributor, HttpStatus.OK);
        } catch (DistributorNotFoundException e) {
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

    /*.................................Add new movie to a city.......................................*/


    @PutMapping(value = "distributor/{email}/{cityName}")
    public ResponseEntity<?> addNewMovie(@PathVariable String email, @PathVariable String cityName, @Valid @RequestBody Movie movie) {
        ResponseEntity responseEntity;
        try {
            Distributor distributor = distributorService.addNewMovie(email, cityName, movie);
            kafkaTemplate.send(TOPIC,distributor);
            responseEntity = new ResponseEntity<Distributor>(distributor, HttpStatus.OK);
        } catch (DistributorNotFoundException e) {
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

    /*.....................................Delete a single Movie..........................................*/


    @DeleteMapping(value = "distributor/{email}/{cityName}/{movieId}")
    public ResponseEntity<?> deleteMovie(@PathVariable String email, @PathVariable String cityName, @PathVariable int movieId) {
        ResponseEntity responseEntity;
        try {
            Distributor distributor = distributorService.deleteMovie(email, cityName, movieId);
            responseEntity = new ResponseEntity<Distributor>(distributor, HttpStatus.OK);
        } catch (DistributorNotFoundException e) {
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

    /*.......................................Get movies by city..............................................*/

    @GetMapping(value = "distributor/{cityName}/getMovies")
    public ResponseEntity<?> getMoviesByCity(@PathVariable String cityName) {
        ResponseEntity responseEntity;
        try {

            List<Movie> movieList;
            movieList = distributorService.getAllMoviesByCity(cityName);
            responseEntity = new ResponseEntity<List<Movie>>(movieList, HttpStatus.OK);
            return responseEntity;
        } catch (Exception ex) {
            responseEntity = new ResponseEntity<String>(ex.getMessage(), HttpStatus.CONFLICT);
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        return responseEntity;
    }

}
