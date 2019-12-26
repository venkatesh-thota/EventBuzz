package com.stackroute.service;

import com.stackroute.exception.UserAlreadyExistsException;
import com.stackroute.domain.UserDetails;
import com.stackroute.exception.NoSuchUserException;
import com.stackroute.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Value("${user-Service.message.exception1}")
    private String serviceException1;

    @Value("${user-Service.message.exception2}")
    private String serviceException2;

    /*Saves UserDetails to Database*/
    @Override
    public UserDetails saveUser(UserDetails userDetails) throws UserAlreadyExistsException {
        if (userRepository.existsById(userDetails.getEmail())) {
            throw new UserAlreadyExistsException(serviceException2);
        } else {
            UserDetails saveduser = userRepository.save(userDetails);
            return saveduser;
        }
    }

    /*Updates Existing UserDetails in DataBase By UserId*/
    @Override
    public UserDetails updateUserById(String id, UserDetails userDetails) throws NoSuchUserException {
        UserDetails newuser = null;
        if (userRepository.existsById(id)) {
            newuser = getUserById(id);
            newuser.setCity(userDetails.getCity());
            newuser.setEmail(userDetails.getEmail());
            newuser.setGender(userDetails.getGender());
            newuser.setName(userDetails.getName());
            newuser.setPhoneNumber(userDetails.getPhoneNumber());
            newuser.setLanguage(userDetails.getLanguage());
            newuser.setGenre(userDetails.getGenre());
            newuser.setWishList(userDetails.getWishList());
            UserDetails savedmovie = userRepository.save(userDetails);
            return savedmovie;
        } else {
            throw new NoSuchUserException(serviceException1);
        }
    }

    /*Fetch Existing UserDetails in DataBase By UserId*/
    @Override
    public UserDetails getUserById(String id) {
        UserDetails userDetails = userRepository.findById(id).get();
        return userDetails;
    }

    /*Deletes Existing UserDetails in DataBase By UserId*/
    @Override
    public Boolean deleteUserById(String id) throws NoSuchUserException {
        if (userRepository.existsById(id) == false) {
            throw new NoSuchUserException(serviceException1);
        } else {
            UserDetails deletedUserDetails = getUserById(id);
            userRepository.delete(deletedUserDetails);
            return true;
        }
    }
}
