package com.stackroute.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;


//Node entity class for user node
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString

@NodeEntity
public class User {

    @Id
    private String email;
    @Property
    private String name;
    @JsonIgnoreProperties
    private String phoneNumber;
    @JsonIgnoreProperties
    private String gender;
    @Property
    private String city;
    @JsonIgnoreProperties
    private String password;
    @Property
    private String[] genre;
    @Property
    private String[] language;
    @JsonIgnoreProperties
    private Movie[] wishList;


}
