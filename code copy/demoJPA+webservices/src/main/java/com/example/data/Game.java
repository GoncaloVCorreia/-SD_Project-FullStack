package com.example.data;

import javax.persistence.Column;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.CascadeType;

@Entity
@XmlRootElement
public class Game implements Serializable{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_id1", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    public Team team1;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_id2", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    public Team team2;

    public String place;
    public String time;
    public int status;
    public int t1Goals;
    public int t2Goals;
    public String currentGameTime;
  
    
    public Game(){}
    public Game(Team team1,Team team2,String place, String time){
        this.team1=team1;
        this.team2=team2;
        this.place=place;
        this.time= time;
        this.status=0;
        this.t1Goals=0;
        this.t2Goals=0;
        this.currentGameTime="";
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Team getteam1() {
        return this.team1;
    }
    public void setteam1(Team team1) {
        this.team1 = team1;
    }
    public Team getteam2() {
        return this.team2;
    }
    public void setteam2(Team team2) {
        this.team2 = team2;
    }
    public String getplace() {
        return this.place;
    }
    public void setplace(String place) {
        this.place = place;
    }
    public String gettime() {
        return this.time;
    }
    public void settime(String time) {
        this.time= time;
    }
    public String getcurrentGameTime() {
        return this.currentGameTime;
    }
    public void setcurrentTime(String currentTime) {
        this.currentGameTime=currentTime;
    }
    public int getstatus() {
        return this.status;
    }
    public void setstatus(int status) {
        this.status= status;
    }
    public int gett1Goals() {
        return this.t1Goals;
    }
    public void sett1Goals(int t1Goals) {
        this.t1Goals+= t1Goals;
    }
    public int gett2Goals() {
        return this.t2Goals;
    }
    public void sett2Goals(int t2Goals) {
        this.t2Goals+= t2Goals;
    }
    public String toString(){
        return "ID " +this.id +" Place "+this.place+" Time "+this.time +" Team 1 -> "+this.getteam1().getName() +" Team 2 -> "+this.getteam2().getName() ;
    }

}
