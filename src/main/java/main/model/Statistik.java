package main.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class Statistik {


     private String name;
     private int elo;
     private int wins;
     private int losses;
     private String userid;
     private String id;


    public Statistik(String name, int elo, int wins, int losses, String userid, String id) {
        this.name = name;
        this.elo = elo;
        this.wins = wins;
        this.losses = losses;
        this.userid = userid;
        this.id = id;
    }
}
