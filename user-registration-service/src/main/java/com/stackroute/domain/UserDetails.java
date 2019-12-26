package com.stackroute.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class UserDetails {
    @Id
    private String email;
    private String name;
    private String phoneNumber;
    private String gender;
    private String city;
    @Transient
    private String password;
    private String genre[];
    private String language[];
    private Movie wishList[];

    @Override
    public String toString() {
        return "UserDetails{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", city='" + city + '\'' +
                ", password='" + password + '\'' +
                ", genre=" + Arrays.toString(genre) +
                ", language=" + Arrays.toString(language) +
                ", wishList=" + Arrays.toString(wishList) +
                '}';
    }
}
