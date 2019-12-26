package com.stackroute.Repository;

import com.stackroute.domain.Producer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//                       Repository class for Spring Boot Application

@Repository
public interface ProducerRepository extends CrudRepository<Producer, String> {
    public Producer findByemail(String email);
}
