package com.example.demo;

import java.util.List;
import java.util.Optional;

//import javax.transaction.Transactional;

import java.util.ArrayList;    
import org.springframework.beans.factory.annotation.Autowired;    
import org.springframework.stereotype.Service;

import com.example.data.Users;

@Service    
public class UserService  
{    
    @Autowired    
    private UserRepo userRepository;

    public List<Users> getAlllUsers()  
    {    
        List<Users>userRecords = new ArrayList<>();    
        userRepository.findAll().forEach(userRecords::add);    
        return userRecords;    
    }

    public void addUser(Users u)  
    {
        System.out.println(u);
        userRepository.save(u);    
    }

    public Optional<Users> getUser(int id) {
        return userRepository.findById(id);
    }

    public Users findUserByCreds(String username, String password){
        return userRepository.findUserByCreds(username, password);
    }
    
}    