package com.stackroute.service;

import com.stackroute.domain.*;
import com.stackroute.repositories.LikesRepository;
import com.stackroute.repositories.MovieRepository;
import com.stackroute.repositories.ShowRepository;
import com.stackroute.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.*;

//Written all methods defined in MovieService
@Service
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;
    private LikesRepository likesRepository;
    private UserRepository userRepository;
    private ShowRepository showRepository;

    private Set<Integer> movieIds = new HashSet<>();


    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, UserRepository userRepository, LikesRepository likesRepository, ShowRepository showRepository) {
        this.likesRepository = likesRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.showRepository = showRepository;
    }

    //Consuming show from Kafka for creation of Movie node as soon as ShowDB gets populated
    @KafkaListener(groupId = "showconsumer", topics = "show-json", containerFactory = "kafkaShowListenerContainerFactory")

    public void createMovieNodes(@Payload Show show) {
        Movie tempMovie = new Movie();
        //showRepository.save(show);
	
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

	System.out.print(show+"SHOW");


        for (Movie movie : show.getMovies()) {

            if(movieIds.add(movie.getMovieId())){
                tempMovie = new Movie();
                tempMovie.setMovieId(movie.getMovieId());
                tempMovie.setMovieTitle(movie.getMovieTitle());
                tempMovie.setYearOfRelease(movie.getYearOfRelease());
                tempMovie.setLanguage(movie.getLanguage());
                tempMovie.setGenre(movie.getGenre());
                tempMovie.setDirector(movie.getDirector());
                tempMovie.setCast(movie.getCast());
                tempMovie.setCity(show.getCityName());
                movieRepository.save(tempMovie);
                movieIds.add(movie.getMovieId());
            }
            else{
                continue;
            }

        }


    }

    //Consuming show from Kafka for updated show topic (scheduler implementation) as soon as ShowDB gets updated
    @KafkaListener(groupId = "showconsumer", topics = "updatedshowjson", containerFactory = "kafkaShowListenerContainerFactory")

    public void updateShow(@Payload Show show){

	    System.out.print(show + "                                                                               " + "UPDATED SHOW");
        System.out.println(showRepository.save(show));
        System.out.print(show + "                                                                               " + "UPDATED SHOW");

    }


    public Show fetchShow(String cityName){
        return showRepository.getShowByCityName(cityName);
    }

    //retreiving list of top 3 movies based on repository CYPHER Query
    @Override
    public List<Movie> topThreeMovies(String city) {

        return this.movieRepository.getTopThreeMovies(city);
    }

    //retreiving a collection of all movies
    @Override
    public Collection<Movie> getAllMovies() {
        return this.movieRepository.getAllMovies();
    }

    //retreiving a list of trending movies by a particular city
    @Override
    public List<Movie> getTrendingMovieByCity(String city) {

        return this.movieRepository.sortByCity(city);
    }

    @Override
    public List<Movie> getTrendingMovieByUserCity(String city) {

        return this.movieRepository.sortByUserCity(city);
    }

    //retreiving a list of movies by a particular language
    @Override
    public List<Movie> getMovieByLanguage(String language) {
        return this.movieRepository.sortByLanguage(language);
    }

    @Override
    public List<Movie> getMovieByUserLanguage(String language) {
        return this.movieRepository.sortByUserLanguage(language);
    }


    @Override
    public Set<Movie> getMovieByCast(String cast)
    {
        return this.movieRepository.sortByCast(cast);
    }

    @Override
    public Set<Movie> getMovieByUserCast(String cast)
    {
        return this.movieRepository.sortByUserCast(cast);
    }

    @Override
    public List<Movie> getMovieByDirector(String director){
        return this.movieRepository.sortByDirector(director);
    }


    @Override
    public List<Movie> getMovieByUserDirector(String director){
        return this.movieRepository.sortByUserDirector(director);
    }

    //establishing relationship between user and movie nodes based on movie ID and User ID details
    @Override
    public void saveLikes(String email, Integer movieId) {
        Likes likes = new Likes();
        User user1 = userRepository.findByUserEmail(email);
        Movie movie1 = movieRepository.findByMovieId(movieId);
        likes.setUser(user1);
        likes.setMovie(movie1);
        likesRepository.save(likes);
    }



    //retreiving a list of movies by genre
    @Override
    public Set<Movie> getMovieByGenre(String genre) {

        return this.movieRepository.sortByGenre(genre);

    }

    //retreiving a list of movies by genre
    @Override
    public Set<Movie> getMovieByUserGenre(String genre) {

        return this.movieRepository.sortByUserGenre(genre);

    }

    //creation of movie nodes based on repository method
    @Override
    public void saveMovie(List<Movie> movies) {
        for (int i = 0; i < movies.size(); i++) {
            movieRepository.save(movies.get(i));
        }
    }

    //creation of user nodes based on repository method
    @Override
    public void saveUser(User user) {
      try{
        userRepository.save(user);
        System.out.println(user+"Repository works");
      }catch(Exception e){
	      e.printStackTrace();
      }
    }
}


