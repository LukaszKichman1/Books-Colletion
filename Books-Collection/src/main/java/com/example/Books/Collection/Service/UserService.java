package com.example.Books.Collection.Service;

import com.example.Books.Collection.Entity.User;
import com.example.Books.Collection.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public Optional<User> findById(int id) {
       return userRepository.findById(id);
    }

}
