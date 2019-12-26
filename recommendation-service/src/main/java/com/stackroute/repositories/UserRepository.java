package com.stackroute.repositories;

import com.stackroute.domain.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//UserRepository incorporating Neo4j CRUD operations, which uses the domain class User of type Long
@Repository
public interface UserRepository extends Neo4jRepository<User, String> {
    @Query("MATCH (user:User) WHERE user.email = {email} RETURN user")
    User findByUserEmail(@Param("email") String email);

}

