package com.stackroute.service;

import com.stackroute.domain.*;
import com.stackroute.exceptions.ShowAlreadyExistsException;
import com.stackroute.exceptions.ShowNotFoundException;
import com.stackroute.Repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowServiceImpl implements ShowService {

    private ShowRepository showRepository;

    private KafkaTemplate<String, Show> kafkaTemplate;
    
    private static final String TOPIC1="updatedshowjson";


    @Autowired
    public ShowServiceImpl(ShowRepository showRepository,KafkaTemplate<String, Show> kafkaTemplate){

        this.showRepository=showRepository;
        
	this.kafkaTemplate=kafkaTemplate;
    }

    @Value("${app.service.message1}")
    private String message1;

    @Value("${app.service.message2}")
    private String message2;


    //this method adds the shows to particular movie in a city
    @Override
    public Show addShow(Show show) throws ShowAlreadyExistsException {

        int movieId=show.getMovies().get(0).getMovieId();
        int flag=1;
        int flag2=0;
        if(showRepository.existsById(show.getCityName())) {
            Show show1 = showRepository.findById(show.getCityName()).get();

            for (int i = 0; i < show1.getMovies().size(); i++) {
                if (show1.getMovies().get(i).getMovieId() == movieId) {
                    flag2=1;
                    List<Theatre> theatreList = show1.getMovies().get(i).getTheatres();
                    for(int j=0;j<theatreList.size();j++){
                        if(theatreList.get(j).getTheatreId() == show.getMovies().get(0).getTheatres().get(0).getTheatreId()){
                            flag=0;
                            break;
                        }
                    }
                    if (flag==1) {
                        show1.getMovies().get(i).getTheatres().add(show.getMovies().get(0).getTheatres().get(0));
                    }
                    break;
                }
            }
            if(flag2==0){
                show1.getMovies().add(show.getMovies().get(0));
            }
            showRepository.save(show1);
            show=show1;
        }
        else {
            showRepository.save(show);
        }

        if(flag ==0) {
            throw new ShowAlreadyExistsException(message2);
        }
        return show;
    }

    //this method returns the toatal shows
    @Override
    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    //this method update the show timings and prices(toatl theatre object
    @Override
    public List<Show> updateShow(String cityName, int movieId, Theatre theatre) throws ShowNotFoundException {
        if(!showRepository.existsById(cityName)){
            throw new ShowNotFoundException(message1);
        }
        Show show1=showRepository.findById(cityName).get();
        int flag=0;
        for(int i = 0; i<show1.getMovies().size(); i++){

            if(show1.getMovies().get(i).getMovieId() == movieId){
                    List<Theatre> theatreList = show1.getMovies().get(i).getTheatres();
                    for(int j=0;j<theatreList.size();j++){
                        if(theatreList.get(j).getTheatreId() == theatre.getTheatreId()){
                            flag=1;
                            show1.getMovies().get(i).getTheatres().get(j).setTimings(theatre.getTimings());
                            break;
                        }
                    }
                break;
            }
        }
        showRepository.save(show1);
        kafkaTemplate.send(TOPIC1,show1);
        if(flag == 0){
            throw new ShowNotFoundException(message1);
        }
        return showRepository.findAll();
    }


    // this method delete the theatre(All shows) for a particular movie
    @Override
    public List<Show> delteShow(String cityName, int movieId, int theatreId) throws ShowNotFoundException {

        if(!showRepository.existsById(cityName)){
            throw new ShowNotFoundException(message1);
        }
        Show show1=showRepository.findById(cityName).get();
        int flag=0;
        for(int i = 0; i<show1.getMovies().size(); i++){

            if(show1.getMovies().get(i).getMovieId() == movieId){

                    List<Theatre> theatreList = show1.getMovies().get(i).getTheatres();
                    for(int j=0;j<theatreList.size();j++){
                        if(theatreList.get(j).getTheatreId() == theatreId){
                            flag=1;
                            show1.getMovies().get(i).getTheatres().remove(j);
                            break;
                        }
                    }
                break;
            }
        }
        showRepository.save(show1);
        kafkaTemplate.send(TOPIC1,show1);
        if(flag == 0){
            throw new ShowNotFoundException(message1);
        }
        return showRepository.findAll();
    }

    // this method will return the list of movies in the city
    @Override
    public List<Movie> getMoviesByCityName(String cityName) throws ShowNotFoundException {
        if(showRepository.existsById(cityName)){
        }
        else throw new ShowNotFoundException(message1);
        return showRepository.getShowByCityName(cityName).getMovies();
    }


    public Show delteShowAfterBooking(ScheduleStopping scheduleStopping) throws ShowNotFoundException{


        if(!showRepository.existsById(scheduleStopping.getCity())){
            throw new ShowNotFoundException(message1);
        }
        Show show1=showRepository.findById(scheduleStopping.getCity()).get();

        int flag=0;
        int flag1=0;
        for(int i = 0; i<show1.getMovies().size(); i++){

            if(show1.getMovies().get(i).getMovieId() == scheduleStopping.getMovieId()){
                flag1=1;
                List<Theatre> theatreList = show1.getMovies().get(i).getTheatres();
                for(int j=0;j<theatreList.size();j++){
                    if(theatreList.get(j).getTheatreId() == scheduleStopping.getTheatreId()){
                        flag=1;
                        List<Timing> timings=show1.getMovies().get(i).getTheatres().get(j).getTimings();
                        for(int k=0;k<timings.size();k++){
                            if(timings.get(k).getShowId().equals(scheduleStopping.getShowId())){
                                timings.remove(k);
                                break;
                            }
                        }
                        System.out.println(timings);
                        show1.getMovies().get(i).getTheatres().get(j).setTimings(timings);
                        break;
                    }
                }
                break;
            }
        }
        showRepository.save(show1);

        kafkaTemplate.send(TOPIC1,show1);
        if(flag1 == 0){
            throw new ShowNotFoundException(message1);
        }
        if(flag == 0){
            throw new ShowNotFoundException(message1);
        }
        return show1;
    }


    @KafkaListener(groupId = "schedulestoppingconsumer",topics="show-scheduler",containerFactory ="kafkaScheduleStoppingListenerContainerFactory")
    public void getShows(@Payload ScheduleStopping scheduleStopping){
        System.out.println("Consumed Json Message: "+ scheduleStopping.toString());
        try {
            Show show=delteShowAfterBooking(scheduleStopping);
        }
        catch (ShowNotFoundException ex){
            System.out.println("Show not found to delete");
        }

    }


}
