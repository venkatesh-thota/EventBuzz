package com.stackroute.controller;

import com.stackroute.domain.Distributor;
import com.stackroute.domain.TokenTags;
import com.stackroute.service.Tokenizer;
import com.stackroute.repository.DistributorRepository;
import com.stackroute.service.EntityRecognition;
import com.stackroute.service.NGramTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Arrays;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "api/v1")
@CrossOrigin("*")
public class SearchController {

    private DistributorRepository distributorRepository;
    private EntityRecognition entityRecognition;
    private Tokenizer tokenizer;

    @Autowired
    public SearchController(DistributorRepository distributorRepository, EntityRecognition entityRecognition,Tokenizer tokenizer) {
        this.distributorRepository = distributorRepository;
        this.entityRecognition = entityRecognition;
        this.tokenizer = tokenizer;
      
    }

    @GetMapping(value="/query/{query}")
    public ResponseEntity<?> postQuery(@PathVariable String query) {
        ResponseEntity responseEntity;
        try{
        //List<String> tokenList =Arrays.asList("%20", "Delhi", "%20", "%20", "%20", "%20");
        
        List<String> tokenList =  new ArrayList<>();
        tokenList = NGramTokenizer.performNGramAnalysis(query);
        TokenTags tokenTags = entityRecognition.findEntityDomain(tokenList);
        responseEntity = new ResponseEntity<TokenTags>(tokenTags, HttpStatus.OK);
        }
        catch (Exception ex) {
            responseEntity = new ResponseEntity<String>(ex.getMessage(), HttpStatus.CONFLICT);
        }

        return responseEntity;
    }
    @KafkaListener(groupId = "kafkadistributor", topics = "distributorlist", containerFactory = "kafkaDistributorListenerContainerFactory")
    public void getUserFrmTopic(@Payload Distributor distributor) {
        distributorRepository.save(distributor);
    }

}
