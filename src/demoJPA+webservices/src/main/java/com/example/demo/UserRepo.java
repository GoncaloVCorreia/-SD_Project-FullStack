package com.example.demo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.data.Users;

public interface UserRepo extends CrudRepository<Users, Integer>   
{ 
    @Query("select u from Users u where u.username=?1 and u.password=?2")
    public Users findUserByCreds(String username,String password);
} 