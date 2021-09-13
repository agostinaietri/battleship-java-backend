package com.codeoftheweb.salvo.model;

import com.codeoftheweb.salvo.model.Game;
import com.codeoftheweb.salvo.model.GamePlayer;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")

    private long id;

    private String userName;

    @OneToMany(mappedBy="player", fetch = FetchType.EAGER)
    Set<GamePlayer> gamePlayers = new HashSet<>();

    @OneToMany(mappedBy="player", fetch = FetchType.EAGER)
    Set<Score> scores = new HashSet<>();

    public void addGamePlayer(GamePlayer gamePlayer) {
        gamePlayer.setPlayer(this);
        gamePlayers.add(gamePlayer);
    }

    public List<Game> getGame() {
        return gamePlayers.stream().map(sub -> sub.getGame()).collect(Collectors.toList());
    }

    public Player() {

    }

    public long getId() {
        return id;
    }

    public Player(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public Optional<Score> getScore(Game game){
        return this.scores.stream().filter(g -> g.getGame().equals(game)).findFirst();
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }
}
