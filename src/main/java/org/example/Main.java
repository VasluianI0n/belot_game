package org.example;

import org.entity.Player;
import org.game.belot.Round;


public class Main {
    public static void main(String[] args) {
        Player[] players = new Player[3];
        players[0] = new Player("Anton");
        players[1] = new Player("Grisa");
        players[2] = new Player("McGregor");

        Round round = new Round(players);
        while(players[0].getTotalBalls()<101 && players[1].getTotalBalls()<101 && players[2].getTotalBalls()<101){
            round.nextRound();
        }
    }
}