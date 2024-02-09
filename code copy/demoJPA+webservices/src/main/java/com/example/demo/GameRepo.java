package com.example.demo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import com.example.data.Game;

public interface GameRepo extends CrudRepository<Game, Integer>   
{ 
    @Query(value="select g from Game g where g.status = ?1")
    public List<Game> findByStatus(Integer status);

    @Query(value="select g from Game g where g.team1.id = ?1 or g.team2.id = ?1")
    public List<Game> findGamesByTeamId(Integer status);

    @Query(value="select g from Game g where g.id = ?1")
    public Game findGamesById(Integer id);
  
} 