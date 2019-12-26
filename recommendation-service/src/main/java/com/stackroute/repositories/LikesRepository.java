package com.stackroute.repositories;

import com.stackroute.domain.Likes;
import com.stackroute.domain.Movie;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

//LikesRepository incorporating Neo4j CRUD operations, which uses the domain class Likes of type Long
@Repository

public interface LikesRepository extends Neo4jRepository<Likes,Long> {
    @Query("MATCH (u:User)-[:LIKES]->(m:Movie) WHERE m.city={city} AND u.email={email} RETURN m")
    public Set<Movie> getMovieByCityNameAndEmail(@Param("city") String city, @Param("email") String email);
}
