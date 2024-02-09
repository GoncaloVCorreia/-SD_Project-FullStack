package com.example.data;
import java.io.Serializable;

import javax.persistence.Column;

//import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
//import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@XmlRootElement
public class Player implements Serializable {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String name, position, age,image;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    public Team trueTeam;
    

	public Player() {
	}
    public Player( String name, String position, String age,Team trueTeam,String image){
       
        this.name=name;
        this.position=position;
        this.age=age;
        this.trueTeam=trueTeam;
        this.image=image;
    }
    public String getimage() {
        return this.image;
    }

    public void setimage(String image) {
        this.image = image;
    }
    public Team gettrueTeam() {
        return trueTeam;
    }

    public void settrueTeam(Team trueTeam) {
        this.trueTeam = trueTeam;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setage(String age) {
        this.age = age;
    }

    public String getage() {
        return this.age;
    }

    public String toString() {
        return this.name + " (id = " + this.id + "). position: " + this.position+ "age: "+this.age+" team-> "+this.gettrueTeam().getName()+"\n";
    }





    
}
