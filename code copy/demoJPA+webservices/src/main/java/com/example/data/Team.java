package com.example.data;

import javax.persistence.Column;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.CascadeType;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@XmlRootElement
public class Team implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String  country,image;
    @Column(unique=true)
    public String name;

    public int victories;
    public int draws;
    public int defeats;
    public int numGames;
    public Team() {}

    public Team(String name, String country, String image) {
        this.name = name;
        this.country = country;
        this.image=image;
        this.victories=0;
        this.draws=0;
        this.defeats=0;
        this.numGames=0;

      
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getcountry() {
        return this.country;
    }

    public void setcountry(String country) {
        this.country = country;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setimage(String image) {
        this.image = image;
    }

    public String getimage() {
        return this.image;
    }
    public int getvictories() {
        return this.victories;
    }

    public void setvictories(int victories) {
        this.victories+= victories;
        this.numGames+=1;
    }
    public int getdraws() {
        return this.draws;
    }

    public void setdraws(int draws) {
        this.draws+= draws;
        this.numGames+=1;
    }
    public int getdefeats() {
        return this.defeats;
    }

    public void setdefeats(int defeats) {
        this.defeats+= defeats;
        this.numGames+=1;
    }
    public int getnumGames(){
        return this.numGames;
    }
    public void setnumGames(int numGames){
        this.numGames+=numGames;
    }

    public String toString() {
        return this.name + " (id = " + this.id + "). country: " + this.country + "image url: "+this.image;
    }

}
