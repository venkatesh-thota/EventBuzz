package com.stackroute.repositories;


import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.stackroute.domain.Movie;
import com.stackroute.domain.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

/*MovieRepository incorporating Neo4j CRUD operations, and also custom CYPHER queries,
all of which uses the domain class Movie of type Long*/
public interface MovieRepository extends Neo4jRepository<Movie, Long> {


    //CYPHER Query for fetching movie by movieId
    @Query("MATCH (movie:Movie) WHERE movie.movieId = {movieId} RETURN movie")
    public Movie findByMovieId(@Param("movieId") Integer movieId);

    //CYPHER Query for fetching TopThreeMovies
    //    @Query("MATCH (m:Movie)<-[:LIKES]-(u:User)" +
    //            " WITH m,count(u) as likes" +
    //            " WHERE likes > 0" +
    //            " RETURN m" + " ORDER BY likes DESC LIMIT 3")
    @Query("MATCH (m:Movie) WHERE m.city = {city} RETURN m ORDER BY m.yearOfRelease DESC LIMIT 3")
    public List<Movie> getTopThreeMovies(@Param("city") String city);

    //CYPHER Query for fetching all Movies
    @Query("MATCH (m:Movie)" + "RETURN m")
    public Collection<Movie> getAllMovies();


    //CYPHER Query for fetching all Movies running in a particular city
    @Query("MATCH (m:Movie) WHERE m.city = {city} RETURN m ORDER BY m.yearOfRelease DESC")
    public List<Movie> sortByCity(@Param("city") String city);

    //CYPHER Query for fetching a list of Movies of a particular language
    @Query("MATCH (m:Movie) WHERE m.language = {language} RETURN m ORDER BY m.yearOfRelease DESC")
    public List<Movie> sortByLanguage(@Param("language") String language);

    //CYPHER Query for fetching a List of Movies based on a list/single genres/genre
    @Query("MATCH (m:Movie) WHERE {genre} IN m.genre RETURN m ORDER BY m.yearOfRelease DESC")
    public Set<Movie> sortByGenre(@Param("genre") String genre);

    @Query("MATCH (m:Movie) WHERE {cast} IN m.cast RETURN m ORDER BY m.yearOfRelease DESC")
    public Set<Movie> sortByCast(@Param("cast") String cast);

    @Query("MATCH (m:Movie) WHERE m.director = {director} RETURN m ORDER BY m.yearOfRelease DESC")
    public List<Movie> sortByDirector(@Param("director") String director);

    //CYPHER Query for fetching all Movies running in a particular city
    @Query("MATCH (m:Movie) WHERE m.city = {city} AND NOT (m)<-[:LIKES]-(:User) RETURN m ORDER BY m.yearOfRelease DESC")
    public List<Movie> sortByUserCity(@Param("city") String city);

    //CYPHER Query for fetching a list of Movies of a particular language
    @Query("MATCH (m:Movie) WHERE m.language = {language} AND NOT (m)<-[:LIKES]-(:User) RETURN m ORDER BY m.yearOfRelease DESC")
    public List<Movie> sortByUserLanguage(@Param("language") String language);

    //CYPHER Query for fetching a List of Movies based on a list/single genres/genre
    @Query("MATCH (m:Movie) WHERE {genre} IN m.genre AND NOT (m)<-[:LIKES]-(:User) RETURN m ORDER BY m.yearOfRelease DESC")
    public Set<Movie> sortByUserGenre(@Param("genre") String genre);

    @Query("MATCH (m:Movie) WHERE {cast} IN m.cast AND NOT (m)<-[:LIKES]-(:User) RETURN m ORDER BY m.yearOfRelease DESC")
    public Set<Movie> sortByUserCast(@Param("cast") String cast);

    @Query("MATCH (m:Movie) WHERE m.director = {director} AND NOT (m)<-[:LIKES]-(:User) RETURN m ORDER BY m.yearOfRelease DESC")
    public List<Movie> sortByUserDirector(@Param("director") String director);
//
//    @Query("MATCH (m:Movie{movie}) WHERE NOT (m)--(:User{user}) RETURN m")
//    public Movie movieAlreadyLiked(@Param("user") User user, @Param("movie") Movie movie);
}
