package com.stackroute.services;

import com.stackroute.domain.Producer;
import com.stackroute.domain.Theatre;
import com.stackroute.exceptions.ProducerAlreadyExistsException;
import com.stackroute.exceptions.ProducerNotFoundException;
import com.stackroute.exceptions.TheatreAlreadyExistException;
import com.stackroute.exceptions.TheatreNotFoundException;
import com.stackroute.Repository.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//                                   Implementation calss for the service

@Service
@Primary
public class ProducerServiceImpl implements ProducerService {

    private ProducerRepository producerRepository;

    @Autowired
    public ProducerServiceImpl(ProducerRepository producerRepository) {
        this.producerRepository = producerRepository;
    }

    @Value("${producer-service-impl.messages.exception1}")
    private String exceptionMessage1;

    @Value("${producer-service-impl.messages.exception2}")
    private String exceptionMessage2;

    @Value("${theatre-service-impl.messages.exception1}")
    private String exceptionMessage3;

    @Value("${theatre-service-impl.messages.exception2}")
    private String exceptionMessage4;


    //Get Producer details
    public Producer getProducerByEmail(String email)throws ProducerNotFoundException {
        if(producerRepository.findByemail(email) == null){
            throw new ProducerNotFoundException(exceptionMessage2);
        }
        else
            return producerRepository.findByemail(email);
    }

    //GetAll Producer
    public List<Producer> getAllProducers(){
        return (List<Producer>)producerRepository.findAll();
    }

    //Add theatre to producer database with producer details
    public Producer addProducer(Producer producer) throws ProducerAlreadyExistsException{
        Producer savedTheatre = producerRepository.save(producer);
        if(savedTheatre == null)
        {
            throw new ProducerAlreadyExistsException(exceptionMessage1);
        }
        return savedTheatre;
    }

    //DeleteProducer

    public boolean deleteProducer(String email) throws ProducerNotFoundException  {
        if(getProducerByEmail(email)==null) {
            String msg="movie-service.controller.movieNotFoundException";
            throw new ProducerNotFoundException(msg);
        }
        else {
            Producer deletedProducer = getProducerByEmail(email);
            producerRepository.delete(deletedProducer);
            return true;
        }
    }


    //Get All theatre from Producer
    public List<Theatre> getTheatreByProducerEmail(String email)throws ProducerNotFoundException {
        Producer temp;
        List<Theatre> theatrelist;
        if(producerRepository.findByemail(email) == null){
            throw new ProducerNotFoundException(exceptionMessage2);
        }
        else{
            temp = producerRepository.findByemail(email);
            theatrelist = temp.getTheatres();
        }
        return theatrelist;
    }

    //Get Theatre By City
    public List<Theatre> getTheatreByProducerEmailAndCity(String email,String cityname) throws ProducerNotFoundException{
        Producer temp;
        List<Theatre> theatrelist;
        List<Theatre> theatrelistByCity = new ArrayList<>();
        if(producerRepository.findByemail(email) == null){
            throw new ProducerNotFoundException(exceptionMessage2);
        }
        else{
            temp = producerRepository.findByemail(email);
            theatrelist = temp.getTheatres();
            for( int i= 0 ; i< theatrelist.size();i++){
                if(theatrelist.get(i).getTheatreCity().equals(cityname)){
                    theatrelistByCity.add(theatrelist.get(i));
                }
            }
        }
        return theatrelistByCity;
    }

    //Add Theatre details to existing theatre List in producerDb
    public Producer addNewTheatre(String email, Theatre theatre) throws ProducerNotFoundException, TheatreAlreadyExistException
    {
        if(getProducerByEmail(email)==null) {
            throw new ProducerNotFoundException(exceptionMessage2);
        }
        else
        {
            Producer producer=getProducerByEmail(email);
            List<Theatre> theatres=new ArrayList<Theatre>();
            theatres=producer.getTheatres();
            for(int i=0;i<theatres.size();i++)
            {
                if(theatre.getTheatreId()==theatres.get(i).getTheatreId())
                    throw new TheatreAlreadyExistException(exceptionMessage3);
            }
            theatres.add(theatre);
            producer.setTheatres(theatres);
            producerRepository.save(producer);
            return producer;
        }
    }

    //Delete Theatre details from existing theatre List in producerDb
    public Producer deleteTheatre(String email, String theatreName) throws ProducerNotFoundException, TheatreNotFoundException
    {
        if(getProducerByEmail(email)==null) {
            throw new ProducerNotFoundException(exceptionMessage2);
        }
        else
        {
            Producer producer=getProducerByEmail(email);
            List<Theatre> theatres=new ArrayList<Theatre>();
            theatres=producer.getTheatres();
            for(int i=0;i<theatres.size();i++)
            {
                if(theatreName.equals(theatres.get(i).getTheatreName()))
                {
                    theatres.remove(i);
                    producer.setTheatres(theatres);
                    producerRepository.save(producer);
                    return producer;
                }
            }
            throw new TheatreNotFoundException(exceptionMessage4);

        }
    }


    //Get theatre by theatre Name
    public Theatre getTheatreByTheatreName(String email,String theatreName)throws ProducerNotFoundException,TheatreNotFoundException{
        if(getProducerByEmail(email)==null) {
            throw new ProducerNotFoundException(exceptionMessage2);
        }
        else
        {
            Producer producer=getProducerByEmail(email);
            List<Theatre> theatres=new ArrayList<Theatre>();
            theatres=producer.getTheatres();
            for(int i=0;i<theatres.size();i++)
            {
                if(theatreName.equals(theatres.get(i).getTheatreName()))
                {
                    Theatre theatreDetail = theatres.get(i);
                    return theatreDetail;
                }
            }
            throw new TheatreNotFoundException(exceptionMessage4);

        }

    }

}

