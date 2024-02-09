package com.example.demo;

import java.util.List;
import java.util.Optional;

//import javax.transaction.Transactional;

import java.util.ArrayList;    
import org.springframework.beans.factory.annotation.Autowired;    
import org.springframework.stereotype.Service;

import com.example.data.EventGame;

@Service    
public class EventGameService  
{    
    @Autowired    
    private EventGameRepo eventRepository;

    public List<EventGame> getAlllEvents()  
    {    
        List<EventGame>userRecords = new ArrayList<>();    
        eventRepository.findAll().forEach(userRecords::add);    
        return userRecords;    
    }

    public void addEvent(EventGame e)  
    {
  
        eventRepository.save(e);    
    }

    public Optional<EventGame> getEvent(int id) {
        return eventRepository.findById(id);
    }

    public List<EventGame> getAllEventsByGameId(int id){
        List<EventGame>userRecords = new ArrayList<>();    
        eventRepository.findByGame(id).forEach(userRecords::add);
        System.out.println(userRecords);
        return userRecords;    
    }
    
}    