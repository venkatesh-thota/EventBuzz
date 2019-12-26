package com.stackroute.controller;

import com.stackroute.service.ShowService;
import com.stackroute.domain.Movie;
import com.stackroute.domain.Show;
import com.stackroute.exceptions.ShowAlreadyExistsException;
import com.stackroute.exceptions.ShowNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1")
@CrossOrigin
public class ShowController {

    private ShowService showService;
    private KafkaTemplate<String, Show> kafkaTemplate;

    private static final String TOPIC="show-json";

    @Autowired
    public ShowController(ShowService showService,KafkaTemplate<String, Show> kafkaTemplate){
        this.showService=showService;
        this.kafkaTemplate=kafkaTemplate;
    }

    // controller method for save show
    @PostMapping("show")
    public ResponseEntity<?> saveShow(@RequestBody Show show){
        ResponseEntity responseEntity;
        try{
            Show show1=showService.addShow(show);
            kafkaTemplate.send(TOPIC,show);
            responseEntity=new ResponseEntity<Show>(show1, HttpStatus.CREATED);
        }catch (ShowAlreadyExistsException ex){
            responseEntity=new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }catch (Exception ex){
            responseEntity=new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    //controller method for get all shows
    @GetMapping("show")
    public ResponseEntity<?> getAllShows(){
        ResponseEntity responseEntity;
        try{
            List<Show> showList=showService.getAllShows();
            responseEntity=new ResponseEntity<List<Show>>(showList, HttpStatus.OK);
        }catch (Exception ex){
            responseEntity=new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    //controller method for get Movies by city name
    @GetMapping("show/{id}")
    public ResponseEntity<?> getMoviesByCityName(@PathVariable(value = "id") String id){

        ResponseEntity responseEntity;

        try{
            List<Movie> movieList =showService.getMoviesByCityName(id);
            responseEntity = new ResponseEntity<List<Movie>>(movieList, HttpStatus.OK);
        }
        catch(ShowNotFoundException ex){
            responseEntity=new ResponseEntity<String>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            responseEntity=new ResponseEntity<String>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }

    // controller method for update show
    @PutMapping("show/{id}")
    public ResponseEntity<?> updateShow(@RequestBody Show show,@PathVariable(value = "id")  int id){

        ResponseEntity responseEntity;

        try{
            List<Show> showList=showService.updateShow(show.getCityName(),show.getMovies().get(0).getMovieId(),show.getMovies().get(0).getTheatres().get(0));
            kafkaTemplate.send(TOPIC,show);
            responseEntity=new ResponseEntity<List<Show>>(showList,HttpStatus.OK);
        }
        catch (ShowNotFoundException ex){
            responseEntity=new ResponseEntity<String>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            responseEntity=new ResponseEntity<String>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }

    //controller method for delete show
    @DeleteMapping("show/{city}/{movieId}/{id}")
    public ResponseEntity<?> deleteShow(@PathVariable(value = "city")  String city,@PathVariable(value = "movieId")  int movieId,@PathVariable(value = "id")  int id){

        ResponseEntity responseEntity;

        try{
            List<Show> showList=showService.delteShow(city,movieId,id);
            responseEntity=new ResponseEntity<List<Show>>(showList,HttpStatus.OK);
        }catch (ShowNotFoundException ex){
            responseEntity=new ResponseEntity<String>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            responseEntity=new ResponseEntity<String>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }

}
