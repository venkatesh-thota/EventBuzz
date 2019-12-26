package com.stackroute.services;

import com.stackroute.domain.Producer;
import com.stackroute.domain.Theatre;
import com.stackroute.exceptions.ProducerAlreadyExistsException;
import com.stackroute.exceptions.ProducerNotFoundException;
import com.stackroute.exceptions.TheatreAlreadyExistException;
import com.stackroute.exceptions.TheatreNotFoundException;
import java.util.List;

//                          Service class to call all methods and crud operaions

public interface ProducerService {
    public List<Producer> getAllProducers();
    public Producer addProducer(Producer producer) throws ProducerAlreadyExistsException;
    public boolean deleteProducer(String email)throws ProducerNotFoundException;
    public Producer getProducerByEmail(String email)throws ProducerNotFoundException;
    public List<Theatre> getTheatreByProducerEmail(String email)throws ProducerNotFoundException;
    public List<Theatre> getTheatreByProducerEmailAndCity(String email,String cityname) throws ProducerNotFoundException;
    public Theatre getTheatreByTheatreName(String email,String theatreName)throws ProducerNotFoundException,TheatreNotFoundException;
    public Producer addNewTheatre(String email, Theatre theatre)throws ProducerNotFoundException, TheatreAlreadyExistException;
    public Producer deleteTheatre(String email, String theatreName) throws ProducerNotFoundException, TheatreNotFoundException;
}
