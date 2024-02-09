package com.example.demo;

import java.util.List;
import java.util.Optional;

import com.example.data.Team;
import com.example.data.Player;
import com.example.data.Users;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("rest")
public class RESTcontroller {
    @Autowired
    TeamService teamService;
    @Autowired
    PlayerService playerService;
    @Autowired
    UserService userService;

    @GetMapping(value = "teams", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Team> getTeams()
    {
        return teamService.getAlllTeams();
    }

    @GetMapping(value = "teams/{id}", produces = {MediaType.APPLICATION_XML_VALUE})
    public Team getTeam(@PathVariable("id") int id) {
        Optional<Team> op = teamService.getTeam(id);
        if (op.isEmpty())
            return null;
        return op.get();
    }

    @PostMapping(value = "teams", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void addTeam(@RequestBody Team t) {
        teamService.addTeam(t);
    }

    @PostMapping(value = "players", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void addPlayer(@RequestBody Player p) {
        playerService.addPlayer(p);
    }

    @GetMapping(value = "players", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Player> getPlayers()
    {
        return playerService.getAllPlayers();
    }

    @GetMapping(value = "players/{id}", produces = {MediaType.APPLICATION_XML_VALUE})
    public Player getPlayer(@PathVariable("id") int id) {
        Optional<Player> op = playerService.getPlayer(id);
        if (op.isEmpty())
            return null;
        return op.get();
    }
    
    @GetMapping(value = "users", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Users> geUsers()
    {
        return userService.getAlllUsers();
    }

}
