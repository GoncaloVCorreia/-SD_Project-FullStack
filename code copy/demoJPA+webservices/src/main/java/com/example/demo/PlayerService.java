package com.example.demo;

import java.util.List;
import java.util.Optional;

//import javax.transaction.Transactional;

import java.util.ArrayList;    
import org.springframework.beans.factory.annotation.Autowired;    
import org.springframework.stereotype.Service;

import com.example.data.Player;

@Service    
public class PlayerService  
{    
    @Autowired    
    private PlayerRepo playerRepository;
    
    public List<Player> getAllPlayers()  
    {    
        List<Player>userRecords = new ArrayList<>();    
        playerRepository.findAll().forEach(userRecords::add);    
        return userRecords;    
    }

    public void addPlayer(Player p)  
    {
        System.out.println(p);
        playerRepository.save(p);    
    }

    public Optional<Player> getPlayer(int id) {
        return playerRepository.findById(id);
    }
    public Player getPlayerNonOptional(int id) {
        return playerRepository.findByIdNonOptional(id);
    }
    public List<Player> getPlayersByTeam(int id){
        List<Player>userRecords = new ArrayList<>();    
        userRecords=playerRepository.findByTeamId(id);
        return userRecords;
    }
    public List<Player> getPlayersByTeams(int id1,int id2){
        List<Player>userRecords = new ArrayList<>();    
        userRecords=playerRepository.findByTeamsIds(id1,id2);
        return userRecords;
    }
    

   

}    