package com.stackroute.Repository;

import com.stackroute.domain.Distributor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributorRepository extends CrudRepository<Distributor,String> {
    public Distributor findByemail( String email);
}
