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

@Entity
@XmlRootElement
public class Users implements Serializable{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @Column(unique=true)
    public String username;
    @Column(unique=true)
    public String email;
    
    public String password,contact;
    public boolean isAdmin;
    
    public Users(){}
    public Users(String username,String password, boolean isAdmin,String email, String contact){
        this.username=username;
        this.password=password;
        this.isAdmin=isAdmin;
        this.email=email;
        this.contact=contact;
    }
    public String getemail(){
        return this.email;
    }
    public void setemail(String email){
        this.email=email;
    }
    public String getcontact(){
        return this.contact;
    }
    public void setcontact(String contact){
        this.contact=contact;
    }
    public int getId(){
        return this.id;
    }
    public void serId(int id){
        this.id=id;
    }
    public String getUsername(){
        return this.username;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public String getPassword(){
        return this.password;
    }
    public void setPassword(String passowrd){
        this.password=passowrd;
    }
    public Boolean getisAdmin(){
        return this.isAdmin;
    }
    public void setisAdmin(Boolean isAdmin){
        this.isAdmin=isAdmin;
    }
}
