package com.example.demo;

import org.hibernate.annotations.SourceType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import com.example.data.Player;

public interface PlayerRepo extends CrudRepository<Player, Integer>   
{
    @Query(value="select p from Player p where p.trueTeam.id = ?1")
    public List<Player> findByTeamId(Integer team_id);
    
    @Query(value="select p from Player p where p.trueTeam.id = ?1 or p.trueTeam.id = ?2")
    public List<Player> findByTeamsIds(Integer team_id1,Integer team_id2);

    @Query(value="select p from Player p where p.id = ?1")
    public Player findByIdNonOptional(Integer id);
 } 
