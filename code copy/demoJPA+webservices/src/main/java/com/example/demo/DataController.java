package com.example.demo;

import org.apache.tomcat.util.threads.StopPooledThreadException;
import org.hibernate.exception.ConstraintViolationException;

//import java.util.List;
//import java.util.Optional;

//import com.example.data.Professor;
//import com.example.data.Student;
//import com.example.formdata.FormData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import net.bytebuddy.agent.builder.AgentBuilder.DescriptionStrategy.SuperTypeLoading;

import java.util.Optional;
import java.io.IOException;
import java.net.*;
import java.rmi.StubNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;
import javax.websocket.server.HandshakeRequest;

import com.example.data.EventGame;
import com.example.data.EventPlayer;
import com.example.data.Player;
import com.example.data.Team;
import com.example.data.Users;
import com.example.data.Game;
import com.example.formdata.FormData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.*;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DataController {
    @Autowired
    TeamService teamService;
    @Autowired
    PlayerService playerService;
    @Autowired
    UserService userService;
    @Autowired
    GameService gameService;
    @Autowired
    EventPlayerService eventService;
    @Autowired
    EventGameService eventGameService;

    @GetMapping("/")
    public String redirect() {
        return "redirect:/welcome";
    }
    
    //pagina inicial
    @GetMapping("/welcome")
    public String welcome(Model m){
        return "welcome";
    }

    //home para admins
    @GetMapping("/home")
    public String home(Model m){
        
        return "home";
    }
    
    //para users normais
    @GetMapping("/homeNotAdmin")
    public String homeNotAdmin(Model m){
        
        return "homeNotAdmin";
    }
    
    //criar data para equipas e jogadores
    @GetMapping("/createData")
    public String createData() throws IOException, InterruptedException{
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        // set `Content-Type` and `Accept` headers
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));   
        headers.set("your_name", "your_key");

        //Create a new HttpEntity
        HttpEntity request= new HttpEntity(headers);

        ArrayList ids=new ArrayList<Integer>();

        //Execute the method writing your HttpEntity to the request
        //Equipas da Premier League
        ResponseEntity<String> response = restTemplate.exchange("https://v3.football.api-sports.io/teams?league=39&season=2021", HttpMethod.GET, request, String.class,1);  

        ObjectMapper objectMapper = new ObjectMapper();
        byte[] jsonData = response.getBody().getBytes();
        //read JSON like DOM Parser
        JsonNode rootNode = objectMapper.readTree(jsonData);
        /*JsonNode idNode = rootNode.path("response");
        System.out.println("team = "+idNode.toString());*/

        //vai buscar a resposta
        JsonNode teams = rootNode.path("response");
        Iterator<JsonNode> elements = teams.elements();
        while(elements.hasNext()){
            JsonNode t = elements.next();

            //vai buscar cada equipa
            JsonNode teamroot =objectMapper.readTree(t.toString().getBytes());
            JsonNode team= teamroot.path("team");

            //vai busca dados de cada equipa
            JsonNode infoRoot =objectMapper.readTree(team.toString().getBytes());

            JsonNode teamName= infoRoot.path("name");
            //System.out.println("Team Name-> "+teamName.toString().replace("\"", ""));
            JsonNode teamCountry= infoRoot.path("country");
            //System.out.println("Country-> "+teamCountry.toString().replace("\"", ""));
            JsonNode teamLogo= infoRoot.path("logo");
            //System.out.println("Logo-> "+teamLogo.toString().replace("\"", ""));
            System.out.println();
            JsonNode id= infoRoot.path("id");
            //System.out.println("ID-> "+id.toString().replace("\"", ""));
            ids.add(Integer.parseInt(id.toString().replace("\"", "")));

            Team newteam =new Team(teamName.toString().replace("\"", ""),teamCountry.toString().replace("\"", ""),teamLogo.toString().replace("\"", ""));
            try{
                this.teamService.addTeam(newteam);
            }catch(Exception e){
                System.out.println("data already done\n");
            }

    
        }
         //Jogadores da Premier League
        for(int id=0;id<ids.size();id++){
            if(id==9|| id==18){
                TimeUnit.SECONDS.sleep(60);
            }
            System.out.println();
            ResponseEntity<String> responsePlayer = restTemplate.exchange("https://v3.football.api-sports.io/players/squads/?team="+ids.get(id), HttpMethod.GET, request, String.class,1);  

            ObjectMapper objectMapperPlayer = new ObjectMapper();
            byte[] jsonDataPlayer = responsePlayer.getBody().getBytes();
            //read JSON like DOM Parser
            JsonNode rootNodePlayer = objectMapperPlayer.readTree(jsonDataPlayer);
            /*JsonNode idNode = rootNode.path("response");
            System.out.println("team = "+idNode.toString());*/

            //vai buscar a resposta
            JsonNode players = rootNodePlayer.path("response");
        
            Iterator<JsonNode> elementsP = players.elements();

            System.out.println("____________SQUAD____________");
            while(elementsP.hasNext()){
                JsonNode p = elementsP.next();
                System.out.println();
                //System.out.println(p.toString());

                JsonNode playerroot =objectMapper.readTree(p.toString().getBytes());

                JsonNode tm= playerroot.path("team");
                JsonNode infoTMRoot =objectMapper.readTree(tm.toString().getBytes());
                String teamName= infoTMRoot.path("name").toString().replace("\"", "");
                System.out.println(teamName);
                Team t=teamService.getTeam(teamName);
        

                JsonNode playersArr= playerroot.path("players");
                for(JsonNode player: playersArr){
                    //System.out.println(player.toString());
                    String name=player.path("name").toString().replace("\"", "");
                    System.out.println(name);
                    String position=player.path("position").toString().replace("\"", "");
                    System.out.println(position);
                    String age=player.path("age").toString().replace("\"", "");
                    System.out.println(age);
                    String photo=player.path("photo").toString().replace("\"", "");
                    System.out.println(photo);
                    Player newPlayer= new Player(name, position, age, t,photo);
                    this.playerService.addPlayer(newPlayer);
                }
                
                System.out.println();
              
            }
            
        }

        return "redirect:/listTeams";

    }

    //criar user
    @GetMapping("/createUser")
    public String signup(Model m){
        m.addAttribute("user", new Users());
        return "signup";

    }
    
    //guardar user
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute Users user){
        //Encriptar password
        System.out.println("Original " +user.password);
        user.password=AES256.encrypt(user.password);
        System.out.println("Enc "+user.password);
        System.out.println("Dec "+AES256.decrypt(user.password));
        try{
            this.userService.addUser(user);
            return "redirect:/welcome";
        } catch(Exception e){
            System.out.println("User already exists");
            return "redirect:/createUser";
        }
        
    }

    //fazer login
    @GetMapping("/login")
    public String login(Model m){

        m.addAttribute("user",new Users());
        
        return "login";

    }

    //salvar equipa
    @PostMapping("/verifylogin")
    public String verifyLogin(@ModelAttribute Users u,Model m){
        //colocar em JPQA
        System.out.println(u.getUsername());
        System.out.println(AES256.encrypt(u.getPassword()));
        Users user = userService.findUserByCreds(u.getUsername(), AES256.encrypt(u.getPassword()));
        System.out.println(user);
        if(user!=null){
            if(user.isAdmin){
                return "redirect:/home";
            }
            else{
               
                return "redirect:/homeNotAdmin";
            }
        }
        
        return "redirect:/welcome";
   }

    //ver jogadores por equipa
    @GetMapping("/squadByTeam")
    public String squadByTeam(@RequestParam(name="id", required=true) int id, Model m) {
        Optional<Team> team=teamService.getTeam(id);
        m.addAttribute("team", team.get().name);
        List<Player> players=playerService.getPlayersByTeam(id);
        System.out.println("TOU AQUI");
        System.out.println(players);
        m.addAttribute("players", players);
        return "squad";
    }   
    
    //listar equipas
    @GetMapping("/listTeams")
    public String listTeams(Model model) throws IOException {

        model.addAttribute("teams", teamService.getAlllTeams());
        return "listTeams";
    }

    //ver equipas em poder editar
    @GetMapping("/listTeamsGuest")
    public String listTeamsGuets(Model model) throws IOException {

        model.addAttribute("teams", teamService.getAlllTeams());
        return "listTeamsGuest";
    }

    //ver jogadores por equipa sem poder editar
    @GetMapping("/squadByTeamGuest")
    public String squadByTeamGuest(@RequestParam(name="id", required=true) int id, Model m) {
        Optional<Team> team=teamService.getTeam(id);
        m.addAttribute("team", team.get().name);
        List<Player> players=playerService.getPlayersByTeam(id);
        System.out.println("TOU AQUI");
        System.out.println(players);
        m.addAttribute("players", players);
        return "squadGuest";
    } 

    //editar jogador
    @GetMapping("/editPlayer")
    public String editPlayer(@RequestParam(name="id", required=true) int id, Model m){
        Optional<Player> op = this.playerService.getPlayer(id);
        if (op.isPresent()) {
            m.addAttribute("newplayer", op.get());
            System.out.println(op.get().getimage());
            m.addAttribute("allTeams", this.teamService.getAlllTeams());
            return "addPlayer";
        }
        else {
            return "redirect:/listTeams";
        }
    }

    //editar equipa
    @GetMapping("/editTeam")
    public String editTeam(@RequestParam(name="id", required=true) int id, Model m){
        Optional<Team> op = this.teamService.getTeam(id);
        if (op.isPresent()) {
            m.addAttribute("newteam", op.get());
           
            return "addTeam";
        }
        else {
            return "redirect:/listTeams";
        }
    }

    //adicionar equipa
    @GetMapping("/addTeam")
    public String addTeam(Model m){
      
        m.addAttribute("newteam", new Team());
        return "addTeam";
    }
    
    //salvar equipa
    @PostMapping("/saveTeam")
    public String saveteam(@ModelAttribute Team t){
        try{
            this.teamService.addTeam(t);
            return "redirect:/listTeams";
        }catch(Exception e){
            System.out.println("User already exists");
            return "redirect:/addTeam";
        }
    }
    
    //adicionar jogador
    @GetMapping("/addPlayer")
    public String addPlayer(Model m){
        m.addAttribute("newplayer", new Player());
        m.addAttribute("allTeams", teamService.getAlllTeams());
        return "addPlayer";
    }
    
    //salvar jogador
    @PostMapping("/savePlayer")
    public String savePlayer(@ModelAttribute Player p){
        try{
            this.playerService.addPlayer(p);;
            return "redirect:/listTeams";
        }catch(Exception e){
            System.out.println("User already exists");
            return "redirect:/addPlayer";
        }
    }

    //adicionar jogo
    @GetMapping("/addGame")
    public String addGame(Model m){
          m.addAttribute("newGame", new Game());
          m.addAttribute("allTeams", teamService.getAlllTeams());
          return "addGame";
      }
      
    //salvar equipa
    @PostMapping("/saveGame")
    public String saveGame(@ModelAttribute Game g){
        try{
            if(g.team1.id==g.team2.id){
                System.out.println("same team");
                return "redirect:/addGame";
            }
            else{
                this.gameService.addGame(g);
                return "redirect:/listGames";
            }
        }catch(Exception e){
            System.out.println("error");
            return "redirect:/addGame";
        }
    }
    
    //listar jogos
    @GetMapping("/listGames")
    public String listGames(Model model) throws IOException {

        model.addAttribute("BeginGames", gameService.getGamesToBegin());
        model.addAttribute("CurrGames", gameService.getCurrentGames());

        model.addAttribute("EndGames", gameService.getEndedGames());

       
        return "listGames";
    }
    
    //listar jogos sem poder adicionar eventos
    @GetMapping("/listGamesGuest")
    public String listGamesGuest(Model model) throws IOException {

        model.addAttribute("BeginGames", gameService.getGamesToBegin());
        model.addAttribute("CurrGames", gameService.getCurrentGames());

        model.addAttribute("EndGames", gameService.getEndedGames());

       
        return "listGamesGuest";
    }
    
    //adicionar evento para um jogador do jogo com Id
    @GetMapping("/eventPForGameId")
    public String eventPForGameId(@RequestParam(name="id", required=true) int id, Model m) {
      
     
        Game g= gameService.getGame(id);
        System.out.println(g);
        System.out.println(g.getteam1().toString());
        System.out.println(g.getteam2().toString());
          
        List<Player> players=playerService.getPlayersByTeams(g.getteam1().getId(), g.getteam2().getId());
        EventPlayer ev=new EventPlayer();
        ev.setgame(g);
        m.addAttribute("newEvent",ev);
        m.addAttribute("allPlayers", players);
        return "addEvent";
    }   

    //adicionar evento para o jogo do jogo com Id
    @GetMapping("/eventGForGameId")
    public String eventGForGameId(@RequestParam(name="id", required=true) int id, Model m) {
      
     
        Game g= gameService.getGame(id);
        System.out.println(g);
        System.out.println(g.getteam1().toString());
        System.out.println(g.getteam2().toString());
          
        EventGame ev=new EventGame();
        ev.setgame(g);

        m.addAttribute("newEvent",ev);
    
        return "addGEvent";
    } 

    //salvar evento de jogador
    @PostMapping("/savePEvent")
    public String savePEvent(@ModelAttribute EventPlayer ev){
        try{
            System.out.println("ver jogo");
            System.out.println("----------------");
            System.out.println(ev.getId());
            System.out.println(ev.geteventType());
            System.out.println(ev.gettime());
            System.out.println(ev.getgame());
            System.out.println(ev.getplayer());
            System.out.println("--------------");
            Game g = gameService.getGame(ev.getgame().getId());
            System.out.println(g);
            
            if(g.status==3){
                System.out.println("Game already ended");
                return "redirect:/listGames";
            }
            if(g.status==0){
                System.out.println("Game haven't started yet");
                return "redirect:/listGames";
            }
            
            System.out.println("vou adicionar");
            this.gameService.changeGameTime(g.getId(), ev.gettime());

            if(ev.geteventType().equals("goal")){
                int teamId=ev.getplayer().gettrueTeam().getId();
                this.gameService.changeGoalCount(g.getId(), teamId); 
            }
            this.eventService.addEvent(ev);

            return "redirect:/listGames";
        }catch(Exception e){
            System.out.println("error");
            return "redirect:/listGames";
        }
    }

    //salvar evento de jogo
    @PostMapping("/saveGEvent")
    public String saveGEvent(@ModelAttribute EventGame ev){
        try{
            System.out.println("ver jogo");
            System.out.println("----------------");
            System.out.println(ev.getId());
            System.out.println(ev.geteventType());
            System.out.println(ev.gettime());
            System.out.println(ev.getgame());
            
            System.out.println("--------------");
            Game g = gameService.getGame(ev.getgame().getId());
            System.out.println(g);
            String eventTime=ev.gettime();
            
            if(g.getstatus()==3){
                System.out.println("Game already ended");
                return "redirect:/listGames";
            }
            if(g.getstatus()!=0 && ev.geteventType().equals("begin")){
                System.out.println("Game already started yet");
                return "redirect:/listGames";
            }
            if(g.getstatus()!=2 && ev.geteventType().equals("resume")){
                System.out.println("Game already started yet");
                return "redirect:/listGames";
            }
            if(g.getstatus()!=1 && ev.geteventType().equals("interrupt")){
                System.out.println("Game already started yet");
                return "redirect:/listGames";
            }
            
            System.out.println("vou adicionar");
            this.eventGameService.addEvent(ev);

            System.out.println(g.getstatus()+"   "+ev.geteventType());
            //0-por comecar, 1- comecou, 2- interrompido,  3- acabou
            if(g.getstatus()==0 && ev.geteventType().equals("begin")){
                System.out.println("comecou");
                this.gameService.changeGameState(g.getId(), 1);
                this.gameService.changeGameTime(g.getId(), "0:0");
            }
            //game ends
            if((g.getstatus()==1 || g.getstatus()==2) && ev.geteventType().equals("end")){
                this.gameService.changeGameState(g.getId(), 3);
                this.gameService.changeGameTime(g.getId(), "Game Ended");

                if(g.gett1Goals()>g.gett2Goals()){
                    this.teamService.incrementWinDrawDefeat(g.getteam1().getId(), g.getteam2().getId(), 0);
                }
                else if(g.gett2Goals()>g.gett1Goals()){
                    this.teamService.incrementWinDrawDefeat(g.getteam1().getId(), g.getteam2().getId(), 1);
                }
                else{
                    this.teamService.incrementWinDrawDefeat(g.getteam1().getId(), g.getteam2().getId(), 2);
                }
            }
            if(g.getstatus()==1 && ev.geteventType().equals("interrupt")){
                System.out.println("comecou");
                this.gameService.changeGameState(g.getId(), 2);
                this.gameService.changeGameTime(g.getId(), "Half Time "+ eventTime);
            }
            if(g.getstatus()==2 && ev.geteventType().equals("resume")){
                System.out.println("comecou");
                this.gameService.changeGameState(g.getId(), 1);
                this.gameService.changeGameTime(g.getId(), "Second Half " + eventTime);
            }

            return "redirect:/listGames";
        }catch(Exception e){
            System.out.println("error");
            return "redirect:/listGames";
        }
    }

    //listar eventos do jogo com ID
    @GetMapping("/listEventsForGameId")
    public String listEventsForGameId(@RequestParam(name="id", required=true) int gameId, Model m){
        List<EventGame>gameEv=eventGameService.getAllEventsByGameId(gameId);
        List<EventPlayer>playerEv=eventService.getAllEventsByGameId(gameId);
        List<Events> allEvents = new ArrayList<>();
        for(int i=0;i<gameEv.size();i++){
            String time= gameEv.get(i).gettime();
            String[] finalTime = time.split(":");
            Events e = new Events(Integer.parseInt(finalTime[0]), Integer.parseInt(finalTime[1]), 0, gameEv.get(i).geteventType());
            allEvents.add(e);
        }
        for(int i=0;i<playerEv.size();i++){
            String time= playerEv.get(i).gettime();
            String[] finalTime = time.split(":");
            Events e = new Events(Integer.parseInt(finalTime[0]), Integer.parseInt(finalTime[1]), 1,playerEv.get(i).getplayer().getName(),playerEv.get(i).getplayer().gettrueTeam().getName(),playerEv.get(i).geteventType());
            System.out.println(e.toString());
            allEvents.add(e);
        }
    
        Collections.sort(allEvents);

        List<String> orderedEvents= new ArrayList<>();
        
        for(int i=0;i<allEvents.size();i++){
            String event;
            if(allEvents.get(i).gettype()==0){
                event=allEvents.get(i).getmin()+" : "+allEvents.get(i).getsec()+", "+allEvents.get(i).getgameState();
            }
            else{
                event=allEvents.get(i).getmin()+" : "+allEvents.get(i).getsec()+", "+allEvents.get(i).getgameState()+" for player "+allEvents.get(i).getplayer()+" team "+allEvents.get(i).getteam();
            }
            orderedEvents.add(event);
        }
    
        m.addAttribute("events", orderedEvents);


        return "listEvents";
    }

    //listar melhor marcador/es
    @GetMapping("/bestScorer")
    public String bestScorer(Model m){
       List<Player> players=new ArrayList<>();
       List<Integer> playersIds= eventService.getBestScorer();

        for(int playerId : playersIds){
            players.add(playerService.getPlayerNonOptional(playerId));
        }
        m.addAttribute("bestScorers", players);
        m.addAttribute("goals", eventService.getBestNumberOfGoals());
        return "bestScorer";
    }

    //listar equipas ordenadas por virorias, empates ou derrotas
    @GetMapping("listTeamStats")
    public String listTeamStats(@RequestParam(name="order", required=true) int order,Model m){
        List<Team> teams= new ArrayList<>();
        if(order==0){
           teams=teamService.sortByNumGames();
        }
        else if(order==1){
            teams=teamService.sortByVictory();
        }
        else if(order==2){
            teams=teamService.sortByDraw();
        }
        else{
            teams=teamService.sortByDefeat();
        }

        m.addAttribute("teams",teams);
        return "listTeamStats";
    }

    //pagina para users nao registados
    @GetMapping("/guest")
    public String guest() throws IOException {
        return "guestPage";
    }

}


