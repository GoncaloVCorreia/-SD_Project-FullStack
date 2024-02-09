package com.example.demo;

import org.hibernate.annotations.SourceType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Pair;

import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;

import com.example.data.EventPlayer;

public interface EventPlayerRepo extends CrudRepository<EventPlayer, Integer>   
{
    @Query(value="select e from EventPlayer e where e.game.id = ?1")
    public List<EventPlayer> findByGame(Integer id);


    @Query(value="select player_id from event_player where event_type='goal' GROUP BY player_id HAVING COUNT (player_id)= (select MAX(mycount) from (select player_id,COUNT(player_id) mycount from event_player where event_type='goal' GROUP BY player_id) AS q)", nativeQuery = true)
    public List<Integer> findBestScorer();

    @Query(value="select COUNT(player_id) from event_player where event_type='goal' GROUP BY player_id HAVING COUNT (player_id)= (select MAX(mycount) from (select player_id,COUNT(player_id) mycount from event_player where event_type='goal' GROUP BY player_id) AS q)", nativeQuery = true)
    public Integer findNumberOfGoals();
} 
