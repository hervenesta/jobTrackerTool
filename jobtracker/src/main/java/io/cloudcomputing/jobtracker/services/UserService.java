package io.cloudcomputing.jobtracker.services;

import io.cloudcomputing.jobtracker.exceptions.UsernameAlreadyExistsException;
import io.cloudcomputing.jobtracker.model.User;
import io.cloudcomputing.jobtracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User newUser){

        try{
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

            //Username has to be unique (exception)
            newUser.setUsername(newUser.getUsername());
            //Make sure that password and confirmPassword match
            // We don't persist or show confirmPassword
            newUser.setConfirmPassword("");
            return userRepository.save(newUser);
        }catch(Exception e){
            throw new UsernameAlreadyExistsException("Username '"+newUser.getUsername()+"' already exists.");
        }

    }
}
