package com.example.demo;

import java.util.List;
import java.util.Optional;

//import javax.transaction.Transactional;

import java.util.ArrayList;    
import org.springframework.beans.factory.annotation.Autowired;    
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;

import com.example.data.Team;

@Service    
public class TeamService   
{    
    @Autowired    
    private TeamRepo teamRepository;

    public List<Team> getAlllTeams()  
    {    
        List<Team>userRecords = new ArrayList<>();    
        teamRepository.findAll().forEach(userRecords::add);    
        return userRecords;    
    }

    public void addTeam(Team t)  
    {
        System.out.println(t);
        teamRepository.save(t);    
    }

    public Optional<Team> getTeam(int id) {
        return teamRepository.findById(id);
    }
    
    public Team getTeam(String name) {
        List<Team>userRecords = getAlllTeams();
        for(Team t:userRecords){
            if(t.name.equals(name)){
                return t;
            }
        }
        return null;
    }
    
    public List<Team> sortByVictory(){
        return teamRepository.findAll(Sort.by(Sort.Direction.DESC, "victories"));
    }
    
    public List<Team> sortByDefeat(){
        return teamRepository.findAll(Sort.by(Sort.Direction.DESC, "defeats"));
    }
    
    public List<Team> sortByDraw(){
        return teamRepository.findAll(Sort.by(Sort.Direction.DESC, "draws"));
    }
    
    public List<Team> sortByNumGames(){
        return teamRepository.findAll(Sort.by(Sort.Direction.DESC, "numGames"));
    }
    
    //status -> 0 - t1 wins, 1 - t2 wins, 3 - draw
    @Transactional
    public void incrementWinDrawDefeat(int idteam1, int idteam2,int status) {
        Optional<Team> t1 = teamRepository.findById(idteam1);
        Optional<Team> t2 = teamRepository.findById(idteam2);
        if (!t1.isEmpty() && !t2.isEmpty() ){
            //t1 wins
            if(status==0){
                t1.get().setvictories(1);
                t2.get().setdefeats(1);
            }
            //t2 wins
            if(status==1){
                t2.get().setvictories(1);
                t1.get().setdefeats(1);
            }
            //draw
            if(status==2){
                t1.get().setdraws(1);
                t2.get().setdraws(1);
            }
        
        }
    }
}    