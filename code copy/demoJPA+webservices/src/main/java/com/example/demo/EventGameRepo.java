package com.example.demo;

import org.hibernate.annotations.SourceType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import com.example.data.EventGame;

public interface EventGameRepo extends CrudRepository<EventGame, Integer>   
{
    @Query(value="select e from EventGame e where e.game.id = ?1")
    public List<EventGame> findByGame(Integer id);
} 
