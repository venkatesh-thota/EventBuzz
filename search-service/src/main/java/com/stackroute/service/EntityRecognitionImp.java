package com.stackroute.service;

import com.stackroute.domain.City;
import com.stackroute.domain.Distributor;
import com.stackroute.domain.Movie;
import com.stackroute.domain.TokenTags;
import com.stackroute.repository.DistributorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EntityRecognitionImp implements EntityRecognition {

    private DistributorRepository distributorRepository;

    @Autowired
    public EntityRecognitionImp(DistributorRepository distributorRepository) {
        this.distributorRepository = distributorRepository;
    }





    @Override
    public TokenTags findEntityDomain(List<String> tokenList){

        TokenTags tokenTags = new TokenTags(" ","NAN"," "," "," "," ");

        Set<String> cityList = new HashSet<>();
        Set<String> genreList = new HashSet<>();
        Set<String> languageList = new HashSet<>();
        Set<String> castList = new HashSet<>();
        Set<String> directorList = new HashSet<>();
        List<Distributor> distributorList = (List<Distributor>) distributorRepository.findAll();


        for (Distributor distributor : distributorList) {
            for (City city : distributor.getCities()) {
                cityList.add(city.getCityName());
                for (Movie movie : city.getMovies()) {
                    languageList.add(movie.getLanguage());
                    directorList.add(movie.getDirector());
                    for (String genre : movie.getGenre()) {
                        genreList.add(genre);
                    }
                    for (String cast : movie.getCast()) {
                        castList.add(cast);
                    }
                }
            }
        }
        for (String token : tokenList) {

            for (String city : cityList) {
                if (city.toLowerCase().matches(token.toLowerCase())) {
                    tokenTags.setCityName(city);
                }
            }
            for (String language : languageList) {
                if (language.toLowerCase().matches(token.toLowerCase())) {
                    tokenTags.setLanguage(language);
                }
            }
            for (String genre : genreList) {
                if (genre.toLowerCase().matches(token.toLowerCase())) {
                   tokenTags.setGenre(genre);
                }
            }
            for (String cast : castList) {
                if (cast.toLowerCase().matches(token.toLowerCase())) {
                    tokenTags.setCast(cast);
                }
            }
            for (String director : directorList) {
                if (director.toLowerCase().matches(token.toLowerCase())) {
                    tokenTags.setDirector(director);
                }
            }

        }

        return tokenTags;
    }

}
