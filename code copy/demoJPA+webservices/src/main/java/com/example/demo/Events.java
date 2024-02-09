package com.example.demo;

public class Events implements Comparable<Events>{

    public int min;
    public int sec;
    //0 -> game 1->player
    public int type;
    public String player;
    public String team;
    public String gameState;
    
    public Events(){};
    public Events(int min,int sec,int type,String gameState){
        this.min=min;
        this.sec=sec;
        this.type=type;
        this.gameState=gameState;
        this.player="";
        this.team="";

    }
    public Events(int min,int sec,int type,String player,String team,String gameState){
        this.min=min;
        this.sec=sec;
        this.type=type;
        this.team=team;
        this.player=player;
        this.gameState=gameState;
        
    }
    public int getmin() {
        return this.min;
    }
    public void setmin(int min) {
        this.min = min;
    }
    public int getsec() {
        return this.sec;
    }
    public void setsec(int sec) {
        this.sec = sec;
    }
    public int gettype() {
        return this.type;
    }
    public void settype(int type) {
        this.type = type;
    }
    public String getteam() {
        return this.team;
    }
    public void setteam(String team) {
        this.team = team;
    }
    public String getplayer() {
        return this.player;
    }
    public void setplayer(String player) {
        this.player = player;
    }
    public String getgameState() {
        return this.gameState;
    }
    public void setgameState(String gameState) {
        this.gameState = gameState;
    }
    public String toString(){
        return "Event ->" +this.gameState+" Time-> "+this.min+":"+this.sec+" Player-> "+this.player +" team-> "+this.team;
    }
    @Override
	public int compareTo(Events e) {
        if(this.getmin()>e.getmin()){
		    return 1;
        }
        else{
            if(this.getmin()<e.getmin()){
                return -1;
            }
            else{
                if(this.getsec()>e.getsec()){
                    return 1;
                }
                else{
                    return -1;
                }
            }
        }
	}
}
