package com.stackroute.service;

import com.stackroute.domain.City;
import com.stackroute.domain.Distributor;
import com.stackroute.domain.Movie;
import com.stackroute.exceptions.*;
import com.stackroute.Repository.DistributorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DistributorServiceImpl implements DistributorService {

    private DistributorRepository distributorRepository;
    private Environment environment;
    private String distributorNotFound = "distributor-service.controller.distributor.notFound";
    private String cityNotFound = "distributor-service.controller.city.notFound";

    @Autowired
    public DistributorServiceImpl(DistributorRepository distributorRepository, Environment environment) {
        this.distributorRepository = distributorRepository;
        this.environment = environment;
    }

    /*..........................................Get a Distributor by Email.................................*/
    public Distributor getDistributorByEmail(String email) throws DistributorNotFoundException {

        if (distributorRepository.findByemail(email) == null) {
            String msg = environment.getProperty(distributorNotFound);
            throw new DistributorNotFoundException(msg);

        }
        Distributor distributor = distributorRepository.findByemail(email);
        return distributor;
    }

    /*..............................................Add a distributor...........................................*/
    public Distributor addDistributor(Distributor distributor) throws DistributorAlreadyExistException {
        Distributor checkDistributor = distributorRepository.findByemail(distributor.getEmail());
        if (checkDistributor != null) {
            String msg = environment.getProperty("distributor-service.controller.distributor.alreadyExists");
            throw new DistributorAlreadyExistException(msg);
        }
        Distributor savedDistributor = distributorRepository.save(distributor);
        return savedDistributor;
    }

    public List<Distributor> getAllDistributors() {
        return (List<Distributor>) distributorRepository.findAll();
    }

    /*..............................................Delete a distributor.........................................*/
    public boolean deleteDistributor(String email) throws DistributorNotFoundException {
        if (getDistributorByEmail(email) == null) {
            String msg = environment.getProperty(distributorNotFound);
            throw new DistributorNotFoundException(msg);
        } else {
            Distributor deletedDistributor = getDistributorByEmail(email);
            distributorRepository.delete(deletedDistributor);
            return true;
        }
    }

    /*.................................................Add a city  todistributor......................................*/

    public Distributor addNewCity(String email, City city) throws DistributorNotFoundException, CityAlreadyExistException {
        if (getDistributorByEmail(email) == null) {
            String msg = environment.getProperty(distributorNotFound);
            throw new DistributorNotFoundException(msg);
        } else {
            Distributor distributor = getDistributorByEmail(email);
            List<City> cities;
            cities = distributor.getCities();
            for (int i = 0; i < cities.size(); i++) {
                if (city.getCityName().equals(cities.get(i).getCityName())) {
                    String msg = environment.getProperty("distributor-service.controller.city.alreadyExists");
                    throw new CityAlreadyExistException(msg);
                }
            }
            cities.add(city);
            distributor.setCities(cities);
            distributorRepository.save(distributor);
            return distributor;
        }
    }

    /*...................................Delete a city from distributor.................................................*/

    public Distributor deleteCity(String email, String cityName) throws DistributorNotFoundException, CityNotFoundException {
        if (getDistributorByEmail(email) == null) {
            String msg = environment.getProperty(distributorNotFound);
            throw new DistributorNotFoundException(msg);
        } else {
            Distributor distributor = getDistributorByEmail(email);
            List<City> cities;
            cities = distributor.getCities();
            for (int i = 0; i < cities.size(); i++) {
                if (cityName.equals(cities.get(i).getCityName())) {
                    cities.remove(i);
                    distributor.setCities(cities);
                    distributorRepository.save(distributor);
                    return distributor;
                }
            }
            String msg = environment.getProperty(cityNotFound);
            throw new CityNotFoundException(msg);

        }
    }

    /*......................................Add New Movie to city............................................................*/

    public Distributor addNewMovie(String email, String cityName, Movie movie) throws DistributorNotFoundException, CityNotFoundException, MovieAlreadyExistException {
        if (getDistributorByEmail(email) == null) {
            String msg = environment.getProperty(distributorNotFound);
            throw new DistributorNotFoundException(msg);
        } else {
            Distributor distributor = getDistributorByEmail(email);
            List<City> cities;
            cities = distributor.getCities();
            for (int i = 0; i < cities.size(); i++) {
                if (cityName.equals(cities.get(i).getCityName())) {
                    List<Movie> movies = cities.get(i).getMovies();
                    for (int j = 0; j < movies.size(); j++) {
                        if ((movie.getMovieId() == movies.get(j).getMovieId())) {
                            String msg = environment.getProperty("distributor-service.controller.movie.alreadyExists");
                            throw new MovieAlreadyExistException(msg);
                        }
                    }
                    movies.add(movie);
                    cities.get(i).setMovies(movies);
                    distributor.setCities(cities);
                    distributorRepository.save(distributor);
                    return distributor;
                }
            }
            String msg = environment.getProperty(cityNotFound);
            throw new CityNotFoundException(msg);
        }
    }

    /*..........................................Delete Movie.................................................*/

    public Distributor deleteMovie(String email, String cityName, int movieId) throws DistributorNotFoundException, CityNotFoundException, MovieNotFoundException {
        if (getDistributorByEmail(email) == null) {
            String msg = environment.getProperty(distributorNotFound);
            throw new DistributorNotFoundException(msg);
        } else {
            Distributor distributor = getDistributorByEmail(email);
            List<City> cities;
            cities = distributor.getCities();
            for (int i = 0; i < cities.size(); i++) {
                if (cityName.equals(cities.get(i).getCityName())) {
                    List<Movie> movies = cities.get(i).getMovies();
                    for (int j = 0; j < movies.size(); j++) {
                        if (movieId == movies.get(j).getMovieId()) {
                            movies.remove(j);
                            cities.get(i).setMovies(movies);
                            distributor.setCities(cities);
                            distributorRepository.save(distributor);
                            return distributor;
                        }

                    }
                    String msg = environment.getProperty("distributor-service.controller.movie.notFound");
                    throw new MovieNotFoundException(msg);
                }
            }
            String msg = environment.getProperty(cityNotFound);
            throw new CityNotFoundException(msg);

        }
    }

    /*..............................Get all movie in a city..............................................*/

    public List<Movie> getAllMoviesByCity(String cityName) {
        List<Distributor> distributorList = (List<Distributor>) distributorRepository.findAll();
        List<Movie> movieList = new ArrayList<>();
        List<City> cityList;
        for (Distributor distributor : distributorList
        ) {
            cityList = distributor.getCities();
            for (City city : cityList
            ) {
                if (city.getCityName().equals(cityName)) {
                    movieList.addAll(city.getMovies());
                }

            }

        }
        return movieList;
    }


}
