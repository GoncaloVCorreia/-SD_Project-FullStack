package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import com.example.data.Team;

public interface TeamRepo extends JpaRepository<Team, Integer>   
{ 

} 
