package com.stackroute.repository;

import com.stackroute.domain.BookingArena;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository extends CrudRepository<BookingArena, String> {

}