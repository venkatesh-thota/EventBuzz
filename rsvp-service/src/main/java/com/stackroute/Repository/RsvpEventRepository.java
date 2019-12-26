package com.stackroute.Repository;

import com.stackroute.domain.RsvpEventProducer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RsvpEventRepository extends CrudRepository<RsvpEventProducer,String> {
    public RsvpEventProducer findByEmail( String email);
}
