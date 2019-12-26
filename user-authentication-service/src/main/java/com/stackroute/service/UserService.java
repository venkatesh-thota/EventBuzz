package com.stackroute.service;

import com.stackroute.domain.User;
import com.stackroute.exception.NoUserExistsException;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User save(User user);

    User findByUserId(String email) throws NoUserExistsException;
}
