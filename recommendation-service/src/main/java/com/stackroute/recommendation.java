package com.stackroute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;


@SpringBootApplication
@EnableNeo4jRepositories("com.stackroute.repositories")
public class recommendation {

    public static void main(String[] args) {
        SpringApplication.run(recommendation.class, args);
    }
}