package com.example.demo;

import java.util.List;
import java.util.Optional;

//import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.example.data.EventPlayer;

@Service    
public class EventPlayerService  
{    
    @Autowired    
    private EventPlayerRepo eventRepository;

    public List<EventPlayer> getAlllEvents()  
    {    
        List<EventPlayer>userRecords = new ArrayList<>();    
        eventRepository.findAll().forEach(userRecords::add);    
        return userRecords;    
    }

    public void addEvent(EventPlayer e)  
    {
  
        eventRepository.save(e);    
    }

    public Optional<EventPlayer> getEvent(int id) {
        return eventRepository.findById(id);
    }

    public List<EventPlayer> getAllEventsByGameId(int id){
        List<EventPlayer>userRecords = new ArrayList<>();    
        eventRepository.findByGame(id).forEach(userRecords::add);
        System.out.println(userRecords);
        return userRecords;    
    }

    public List<Integer>  getBestScorer(){
      
        System.out.println("Scorer" +eventRepository.findBestScorer());
        return eventRepository.findBestScorer();
       
    }
    public Integer  getBestNumberOfGoals(){
      
        System.out.println("Goals" +eventRepository.findNumberOfGoals());
        return eventRepository.findNumberOfGoals();
       
    }
}    