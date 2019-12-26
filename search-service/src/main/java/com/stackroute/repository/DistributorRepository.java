package com.stackroute.repository;

import com.stackroute.domain.Distributor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributorRepository extends CrudRepository<Distributor, String> {
    Distributor findByemail(String email);
}
