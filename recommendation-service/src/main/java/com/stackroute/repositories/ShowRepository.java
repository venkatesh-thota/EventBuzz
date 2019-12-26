package com.stackroute.repositories;
import com.stackroute.domain.Show;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowRepository extends MongoRepository<Show,String> {

    public Show getShowByCityName(String cityName);
}