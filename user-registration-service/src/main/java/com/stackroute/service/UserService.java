package com.stackroute.service;

import com.stackroute.exception.UserAlreadyExistsException;
import com.stackroute.domain.UserDetails;
import com.stackroute.exception.NoSuchUserException;

public interface UserService {
    UserDetails saveUser(UserDetails userDetails) throws UserAlreadyExistsException;

    UserDetails updateUserById(String id, UserDetails userDetails) throws NoSuchUserException;

    UserDetails getUserById(String id);

    Boolean deleteUserById(String id) throws NoSuchUserException;
}
