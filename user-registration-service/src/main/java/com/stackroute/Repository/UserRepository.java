package com.stackroute.Repository;

import com.stackroute.domain.UserDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends MongoRepository<UserDetails,String>{
}
