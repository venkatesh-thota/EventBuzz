package com.stackroute.controller;

import com.stackroute.domain.Likes;
import com.stackroute.domain.Movie;
import com.stackroute.domain.Show;
import com.stackroute.domain.User;
import com.stackroute.repositories.LikesRepository;
import com.stackroute.repositories.MovieRepository;
import com.stackroute.repositories.UserRepository;
import com.stackroute.service.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//controller for processing all recommendation requests and populating the entire database
@RestController
@RequestMapping("/recommendation")
@CrossOrigin("*")
public class Controller {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private MovieRepository movieRepo;

    @Autowired
    private LikesRepository likesRepository;


    @Autowired
    private MovieServiceImpl service;


    //Following method creates likes relationship between movie and user whenever a user likes a particular movie

    @PostMapping("/saveMovie")
    public void saveMovie(@RequestBody Show show) {

        service.saveMovie(show.getMovies());
    }

    //creating user nodes based on registration
    @PostMapping("/saveUser")
    public void saveUser(@RequestBody User user) {
        System.out.println(user + "Before calling service");
        service.saveUser(user);
        System.out.println(user + "After call");

    }

    //creating relationships as soon as the registered user likes a particular movie
    @PostMapping("/addLikes")
    public void saveLikes(@RequestBody Likes likes) {
        int movieId = likes.getMovie().getMovieId();
        String email = likes.getUser().getEmail();
        service.saveLikes(email, movieId);
    }

    //default
    @GetMapping("/defaultList/{email}/{city}/{language}/{genre}/{cast}/{director}")
    public Show retreiveMovies(@PathVariable("city") String city, @PathVariable("language") String language, @PathVariable("genre")
            String[] genre, @PathVariable("cast") String[] cast, @PathVariable("director") String director,
                               @PathVariable("email") String email) {

        //
        User user = userRepo.findByUserEmail(email); //fetch selected user
        Set<Movie> likedMovie = likesRepository.getMovieByCityNameAndEmail(city, email); //listOfAlreadyLikedMovies
        List<Movie> likedMovies = new ArrayList<>();
        likedMovies.addAll(likedMovie);
        Set<Integer> movieIds = new HashSet<>();

        Show dummyShow = new Show();
        dummyShow.setCityName(city);
        Show originalShow = service.fetchShow(city); //fetch shows running in the same city
        List<Movie> originalMovies = originalShow.getMovies();
        Set<Movie> finalMovies = new HashSet<>();
        //

            List<Movie> movieNodesByCity = service.getTrendingMovieByUserCity(city);
     /*       for (int i = 0; i < movieNodesByCity.size(); i++) {
                for (int j = 0; j < originalMovies.size(); j++) {
                    if (movieNodesByCity.get(i).getMovieId() == originalMovies.get(j).getMovieId()) {
                        originalMovies.get(i).setCity(movieNodesByCity.get(i).getCity());
                        finalMovies.add(originalMovies.get(j));
                    }
                }
            }*/


        //
        if(language.equals("%20")) {
            String random1 = "";
        }
        else {
            if (likedMovies.size() != 0) {
                for (int k = 0; k < likedMovies.size(); k++) {
                    if (language == likedMovies.get(k).getLanguage()) {
                        for (int a = 0; a < movieNodesByCity.size(); a++) {
                            List<Movie> movieNodesByLanguage = service.getMovieByUserLanguage(language);
                            for (int b = 0; b < movieNodesByLanguage.size(); b++) {
                                if (movieNodesByCity.get(a).getCity() == movieNodesByLanguage.get(b).getCity()) {
                                    for (int i = 0; i < movieNodesByLanguage.size(); i++) {
                                        if (movieIds.add(movieNodesByLanguage.get(i).getMovieId())) {
                                            for (int j = 0; j < originalMovies.size(); j++) {
                                                System.out.println(movieNodesByLanguage.get(i));
                                                if (movieNodesByLanguage.get(i).getMovieId() == originalMovies.get(j).getMovieId()) {
                                                    originalMovies.get(i).setCity(movieNodesByLanguage.get(i).getCity());
                                                    finalMovies.add(originalMovies.get(j));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            for(int a=0; a<movieNodesByCity.size(); a++) {
                List<Movie> movieNodesByLanguage = service.getMovieByUserLanguage(language);
                for (int b = 0; b < movieNodesByLanguage.size(); b++) {
                    if(movieNodesByCity.get(a).getCity() == movieNodesByLanguage.get(b).getCity())
                    for (int i = 0; i < movieNodesByLanguage.size(); i++) {
                        if (movieIds.add(movieNodesByLanguage.get(i).getMovieId())) {
                            for (int j = 0; j < originalMovies.size(); j++) {
                                if (movieNodesByLanguage.get(i).getMovieId() == originalMovies.get(j).getMovieId()) {

                                    originalMovies.get(i).setCity(movieNodesByLanguage.get(i).getCity());
                                    finalMovies.add(originalMovies.get(j));
                                }
                            }
                        }
                    }
                }
            }
        }

        //
        if(genre.toString().equals("%20")){
            String random3 = "";
        }
        else {
            Set<Movie> movieNodesByGenres = new HashSet<>();
            for (int i = 0; i < genre.length; i++) {
                movieNodesByGenres = service.getMovieByUserGenre(genre[i]);
            }
            List<Movie> movieNodesByGenre = new ArrayList<>();
            movieNodesByGenre.addAll(movieNodesByGenres);
            for (int a = 0; a < movieNodesByCity.size(); a++) {
                for (int b = 0; b < movieNodesByGenre.size(); b++) {
                    if (movieNodesByCity.get(a).getCity() == movieNodesByGenre.get(b).getCity()) {
                        for (int i = 0; i < movieNodesByGenre.size(); i++) {
                            if (movieIds.add(movieNodesByGenre.get(i).getMovieId())) {
                                for (int j = 0; j < originalMovies.size(); j++) {
                                    if (movieNodesByGenre.get(i).getMovieId() == originalMovies.get(j).getMovieId()) {

                                        originalMovies.get(i).setCity(movieNodesByGenre.get(i).getCity());
                                        finalMovies.add(originalMovies.get(j));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //
        if(cast.toString().equals("%20")){
            String random3 = "";
        }
        else {
            Set<Movie> movieNodesByCasts = new HashSet<>();
            for (int i = 0; i < cast.length; i++) {
                movieNodesByCasts = service.getMovieByUserCast(cast[i]);
            }
            List<Movie> movieNodesByCast = new ArrayList<>();
            movieNodesByCast.addAll(movieNodesByCasts);
            for (int a = 0; a < movieNodesByCity.size(); a++) {
                for (int b = 0; b < movieNodesByCast.size(); b++) {
                    if (movieNodesByCity.get(a).getCity() == movieNodesByCast.get(b).getCity()) {
                        for (int i = 0; i < movieNodesByCast.size(); i++) {
                            if (movieIds.add(movieNodesByCast.get(i).getMovieId())) {
                                for (int j = 0; j < originalMovies.size(); j++) {
                                    if (movieNodesByCast.get(i).getMovieId() == originalMovies.get(j).getMovieId()) {

                                        originalMovies.get(i).setCity(movieNodesByCast.get(i).getCity());
                                        finalMovies.add(originalMovies.get(j));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //
        if(director.equals("%20")) {
            String random1 = "";
        }
        else {
            if (likedMovies.size() != 0) {
                for (int k = 0; k < likedMovies.size(); k++) {
                    if (language == likedMovies.get(k).getDirector()) {
                        for (int a = 0; a < movieNodesByCity.size(); a++) {
                            List<Movie> movieNodesByDirector = service.getMovieByUserDirector(director);
                            for (int b = 0; b < movieNodesByDirector.size(); b++) {
                                if (movieNodesByCity.get(a).getCity() == movieNodesByDirector.get(b).getCity()) {
                                    for (int i = 0; i < movieNodesByDirector.size(); i++) {
                                        if (movieIds.add(movieNodesByDirector.get(i).getMovieId())) {
                                            for (int j = 0; j < originalMovies.size(); j++) {
                                                System.out.println(movieNodesByDirector.get(i));
                                                if (movieNodesByDirector.get(i).getMovieId() == originalMovies.get(j).getMovieId()) {
                                                    originalMovies.get(i).setCity(movieNodesByDirector.get(i).getCity());
                                                    finalMovies.add(originalMovies.get(j));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            for(int a=0; a<movieNodesByCity.size(); a++) {
                List<Movie> movieNodesByDirector = service.getMovieByUserDirector(director);
                for (int b = 0; b < movieNodesByDirector.size(); b++) {
                    if(movieNodesByCity.get(a).getCity() == movieNodesByDirector.get(b).getCity())
                        for (int i = 0; i < movieNodesByDirector.size(); i++) {
                            if (movieIds.add(movieNodesByDirector.get(i).getMovieId())) {
                                for (int j = 0; j < originalMovies.size(); j++) {
                                    if (movieNodesByDirector.get(i).getMovieId() == originalMovies.get(j).getMovieId()) {

                                        originalMovies.get(i).setCity(movieNodesByDirector.get(i).getCity());
                                        finalMovies.add(originalMovies.get(j));
                                    }
                                }
                            }
                        }
                }
            }
        }

        List<Movie> moviesWithAllDetails = new ArrayList<>();
        moviesWithAllDetails.addAll(finalMovies);
        dummyShow.setMovies(moviesWithAllDetails);
	    System.out.println(dummyShow + "                 " + "USER PROFILE SHOW");
        return dummyShow;
    }


    //retreiving all movies running in a particular city
    @GetMapping("/sortCity/{city}")
    public Show sortCity(@PathVariable("city") String city) {
        Show show = new Show();
        Set<Movie> showMovies = new HashSet<>();
        List<Movie> finalMovies = new ArrayList<>();
        Show show1 = service.fetchShow(city);
        show.setCityName(city);
        List<Movie> movies = service.getTrendingMovieByCity(city);
        List<Movie> movies1 = show1.getMovies();
        for (int i = 0; i < movies.size(); i++) {
            for (int j = 0; j < movies1.size(); j++) {
                if (movies.get(i).getMovieId() == movies1.get(j).getMovieId()) {
                    movies1.get(j).setCity(movies.get(i).getCity());
                    showMovies.add(movies1.get(j));
                }
            }
        }
        finalMovies.addAll(showMovies);
        show.setMovies(finalMovies);
        System.out.println(show);
        System.out.println(finalMovies);
        return show;
    }


    //sorting show based on user selected genre
    @GetMapping("/sortGenre/{genre}/{city}")
    public Show sortGenre(@PathVariable("genre") String[] genre, @PathVariable("city") String city) {

        Show show = new Show();
        Show show1 = service.fetchShow(city); //fetch entire show details
        show.setCityName(city); //set dummy show object to current city name, for fetching movie nodes

        Set<Movie> movies1 = new HashSet<>(); //dummy movie set for storing unique movie nodes sorted by genre
        Set<Movie> movieSetFinal = new HashSet<>(); //dummy movie set for retreiving entire show details
        List<Movie> allMovies = show1.getMovies(); //list of all movies from the entire show details fetched by kafka
        List<Movie> sortedMovies = new ArrayList<>(); //list of sorted movie nodes
        List<Movie> finalMovies = new ArrayList<>(); //list of final movies to be stored in Show which will be returned to frontend
        // that stores movieSet containing all details

        for (int i = 0; i < genre.length; i++) {

            Set<Movie> movies = this.service.getMovieByGenre(genre[i]);

            for (Movie movie : movies) {

                String sortedCity = movie.getCity();
                String inputCity = city;

                if (inputCity.equals(sortedCity)) {
                    movies1.add(movie);
                }
            }
        }

        sortedMovies.addAll(movies1);

        for (int i = 0; i < sortedMovies.size(); i++) {
            for (int j = 0; j < allMovies.size(); j++) {
                if (sortedMovies.get(i).getMovieId() == allMovies.get(j).getMovieId()) {
                    allMovies.get(j).setCity(sortedMovies.get(i).getCity());
                    movieSetFinal.add(allMovies.get(j));
                }
            }
        }
        finalMovies.addAll(movieSetFinal);
        show.setMovies(finalMovies);

        return show;
    }




    //sorting show based on user selected list of casts
    @GetMapping("/sortCast/{cast}/{city}")
    public Show sortCast(@PathVariable("cast") String[] cast, @PathVariable("city") String city) {

        Show show = new Show();
        Show show1 = service.fetchShow(city); //fetch entire show details
        show.setCityName(city); //set dummy show object to current city name, for fetching movie nodes

        Set<Movie> movies1 = new HashSet<>(); //dummy movie set for storing unique movie nodes sorted by cast
        Set<Movie> movieSetFinal = new HashSet<>(); //dummy movie set for retreiving entire show details
        List<Movie> allMovies = show1.getMovies(); //list of all movies from the entire show details fetched by kafka
        List<Movie> sortedMovies = new ArrayList<>(); //list of sorted movie nodes
        List<Movie> finalMovies = new ArrayList<>(); //list of final movies to be stored in Show which will be returned to frontend
        // that stores movieSet containing all details

        for (int i = 0; i < cast.length; i++) {

            Set<Movie> movies = this.service.getMovieByCast(cast[i]);

            for (Movie movie : movies) {

                String sortedCity = movie.getCity();
                String inputCity = city;

                if (inputCity.equals(sortedCity)) {
                    movies1.add(movie);
                }
            }
        }

        sortedMovies.addAll(movies1);

        for (int i = 0; i < sortedMovies.size(); i++) {
            for (int j = 0; j < allMovies.size(); j++) {
                if (sortedMovies.get(i).getMovieId() == allMovies.get(j).getMovieId()) {
                    allMovies.get(j).setCity(sortedMovies.get(i).getCity());
                    movieSetFinal.add(allMovies.get(j));
                }
            }
        }
        finalMovies.addAll(movieSetFinal);
        show.setMovies(finalMovies);

        return show;
    }


    //sorting show based on user selected language
    @GetMapping("/sortLanguage/{language}/{city}")
    public Show sortLanguage(@PathVariable("language") String language, @PathVariable("city") String city) {

        Show show = new Show();
        Show show1 = service.fetchShow(city);
        List<Movie> allMovies = show1.getMovies();
        List<Movie> movieNodes = new ArrayList<>();
        List<Movie> finalMovies = new ArrayList<>();
        show.setCityName(city);
        Set<Movie> sortedMovieNodes = new HashSet<>();
        Set<Movie> movieSetFinal = new HashSet<>();

        //retreiving a list of all movies by a particular language
        List<Movie> movies = service.getMovieByLanguage(language);

        //sorting list of movies with a particular language by the input city
        for (int i = 0; i < movies.size(); i++) {


            String inputCity = city;
            String sortedCity = movies.get(i).getCity();

            if (inputCity.equals(sortedCity)) {
                sortedMovieNodes.add(service.getMovieByLanguage(language).get(i));
            }

        }
        movieNodes.addAll(sortedMovieNodes);

        for (int i = 0; i < movieNodes.size(); i++) {
            for (int j = 0; j < allMovies.size(); j++) {
                if (movieNodes.get(i).getMovieId() == allMovies.get(j).getMovieId()) {
                    allMovies.get(j).setCity(movieNodes.get(i).getCity());
                    movieSetFinal.add(allMovies.get(j));
                }
            }
        }
        finalMovies.addAll(movieSetFinal);
        show.setMovies(finalMovies);
        return show;
    }


    //sorting show based on user selected director
    @GetMapping("/sortDirector/{director}/{city}")
    public Show sortDirector(@PathVariable("director") String director, @PathVariable("city") String city) {

        Show show = new Show();
        Show show1 = service.fetchShow(city);
        List<Movie> allMovies = show1.getMovies();
        List<Movie> movieNodes = new ArrayList<>();
        List<Movie> finalMovies = new ArrayList<>();
        show.setCityName(city);
        Set<Movie> sortedMovieNodes = new HashSet<>();
        Set<Movie> movieSetFinal = new HashSet<>();

        //retreiving a list of all movies by a particular director
        List<Movie> movies = service.getMovieByDirector(director);

        //sorting list of movies with a particular director by the input city
        for (int i = 0; i < movies.size(); i++) {


            String inputCity = city;
            String sortedCity = movies.get(i).getCity();

            if (inputCity.equals(sortedCity)) {
                sortedMovieNodes.add(service.getMovieByDirector(director).get(i));
            }

        }
        movieNodes.addAll(sortedMovieNodes);

        for (int i = 0; i < movieNodes.size(); i++) {
            for (int j = 0; j < allMovies.size(); j++) {
                if (movieNodes.get(i).getMovieId() == allMovies.get(j).getMovieId()) {
                    allMovies.get(j).setCity(movieNodes.get(i).getCity());
                    movieSetFinal.add(allMovies.get(j));
                }
            }
        }
        finalMovies.addAll(movieSetFinal);
        show.setMovies(finalMovies);
        return show;
    }

    //retreiving all movies running in a particular city
    @GetMapping("/sortDefault/{city}")
    public Show sortDefault(@PathVariable("city") String city) {
        Show show = new Show();
        Set<Movie> showMovies = new HashSet<>();
        List<Movie> finalMovies = new ArrayList<>();
        Show show1 = service.fetchShow(city);
        show.setCityName(city);
        List<Movie> movies = service.topThreeMovies(city);
        List<Movie> movies1 = show1.getMovies();
        for(int i=0; i<movies.size(); i++){
            for(int j=0; j<movies1.size(); j++) {
                if (movies.get(i).getMovieId() == movies1.get(j).getMovieId()) {
                    movies1.get(j).setCity(movies.get(i).getCity());
                    showMovies.add(movies1.get(j));
                }
            }
        }
        finalMovies.addAll(showMovies);
        show.setMovies(finalMovies);
        System.out.println(show);
        System.out.println(finalMovies);
        return show;
    }


    //retreiving top 3 movies by user likes and city
//    @GetMapping("/default/{city}")
//    public Show getTopThreeMovies(@PathVariable String city) {
//
//        Show show = new Show();
//        show.setCityName(city);
//        List<Movie> initialMovies = service.topThreeMovies(city);
//        Set<Movie> topThreeMovie = new HashSet<>();
//        List<Movie> finalMovies = new ArrayList<>();
//        Show originalShow = service.fetchShow(city);
//        for (int i = 0; i < initialMovies.size(); i++) {
//            for (int j = 0; j < originalShow.getMovies().size(); j++) {
//                if (originalShow.getMovies().get(j) == initialMovies.get(i)) {
//                    show.setCityName(originalShow.getCityName());
//                    topThreeMovie.add(originalShow.getMovies().get(j));
//                }
//            }
//        }
//
//        finalMovies.addAll(topThreeMovie);
//        show.setMovies(finalMovies);
//
//        return show;
//
//    }

}




