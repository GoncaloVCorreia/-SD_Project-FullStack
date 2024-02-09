package com.example.demo;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import java.util.ArrayList;    
import org.springframework.beans.factory.annotation.Autowired;    
import org.springframework.stereotype.Service;

import com.example.data.Game;

@Service    
public class GameService  
{    
    @Autowired    
    private GameRepo gameRespository;
    
    public List<Game> getAllGame()  
    {    
        List<Game>userRecords = new ArrayList<>();    
        gameRespository.findAll().forEach(userRecords::add);    
        return userRecords;    
    }

    public void addGame(Game g)  
    {
        System.out.println(g);
        gameRespository.save(g);    
    }

    public Game getGame(int id) {
      
        return gameRespository.findGamesById(id);
    }
    
    public List<Game> getGamesToBegin(){
        List<Game>userRecords = new ArrayList<>();    
        gameRespository.findByStatus(0).forEach(userRecords::add);
        //System.out.println("to begin");
       // System.out.println(userRecords);
        return userRecords;   
    }
    public List<Game> getCurrentGames(){
        List<Game>userRecords = new ArrayList<>();    
        gameRespository.findByStatus(1).forEach(userRecords::add);
        gameRespository.findByStatus(2).forEach(userRecords::add);
        //System.out.println("current");
        //System.out.println(userRecords);
        return userRecords;   
    }
    public List<Game> getEndedGames(){
        List<Game>userRecords = new ArrayList<>();    
        gameRespository.findByStatus(3).forEach(userRecords::add);
        //System.out.println("ended");
        //System.out.println(userRecords);
        return userRecords;   
    }
    @Transactional
    public void changeGameState(int id, int newStatus) {
        Optional<Game> g = gameRespository.findById(id);
        if (!g.isEmpty())
            g.get().setstatus(newStatus);
    }
    @Transactional
    public void changeGameTime(int id, String newTime) {
        Optional<Game> g = gameRespository.findById(id);
        if (!g.isEmpty())
            g.get().setcurrentTime(newTime);
    }
    @Transactional
    public void changeGoalCount(int idGame, int idTeam) {
        Optional<Game> g = gameRespository.findById(idGame);
        if (!g.isEmpty())
            if(idTeam==g.get().getteam1().getId()){
                g.get().sett1Goals(1);
            }else{
                g.get().sett2Goals(1);
            }
    }


}    