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
public class EventPlayer implements Serializable{
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    public Game game;

    public String eventType;
    public String time;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    public Player player;
  
    
    public EventPlayer(){}

    public EventPlayer(String eventType,String time,Player player,Game game){
        this.eventType=eventType;
        this.time=time;
        this.player=player;
        this.game=game;
      
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String gettime() {
        return this.time;
    }
    public void settime(String time) {
        this.time= time;
    }
    public String geteventType() {
        return eventType;
    }
    public void seteventType(String eventType) {
        this.eventType = eventType;
    }
    public Player getplayer() {
        return this.player;
    }
    public void setplayer(Player player) {
        this.player = player;
    }
    public Game getgame() {
        return this.game;
    }
    public void setgame(Game game) {
        this.game = game;
    }
    public String toString(){
        return "ID " +this.id +" Time "+this.time+" Game "+this.getgame().getId() +" Player -> "+this.getplayer().toString();
    }



}
