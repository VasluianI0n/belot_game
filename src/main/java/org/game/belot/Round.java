package org.game.belot;

import org.Utils.Consts;
import org.entity.Player;
import org.entity.Stack;
import org.entity.TableCards;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Round {
    
    private Player[] players;
    private Stack stack;
    private int round;

    TableCards tableCards;
    Scanner scanner;

    public Round(Player[] players) {
        this.players = players;
        round = 0;
        tableCards = new TableCards();
    }

    public void nextRound(){
        scanner = new Scanner(System.in);
        stack = new Stack();
        stack.printStackOfCards();
        stack.shuffle();
        stack.printStackOfCards();
        players[round%3].setDealer(true);
        giveFirstCards();
        printPlayingCard();
        giveLastCards();
        setCardsValue();
        beginRound();
        endRound();
        players[round%3].setDealer(false);
        round++;

    }

    private void endRound() {
        int roundPoints = 16;
        int playing = checkPlayingPlayerPos();
        List<Integer> balls = new ArrayList<Integer>();
        int comparator = 0;
        balls.add(pointsToBalls(players[(playing+1)%3].getPoints()));
        balls.add(pointsToBalls(players[(playing+2)%3].getPoints()));
        if(players[(playing+1)%3].getPoints()<players[(playing+2)%3].getPoints()){
            comparator = 1;
        }
        if(balls.get(comparator)>roundPoints-(balls.get(0)+balls.get(1))){
            balls.add(comparator, balls.get(comparator)+roundPoints-(balls.get(0)+balls.get(1)));
            players[(playing+1)%3].giveBalls(balls.get(0));
            players[(playing+2)%3].giveBalls(balls.get(1));
            players[playing].giveBolt();
            System.out.println(players[(playing+1)%3].getPlayerName()+": "+ players[(playing+1)%3].getTotalBalls() + " balls");
            System.out.println(players[(playing+2)%3].getPlayerName()+": "+ players[(playing+2)%3].getTotalBalls() + " balls");
            if(players[playing].getBolt() == 0){
                System.out.println(players[playing].getPlayerName()+": "+ players[playing].getTotalBalls() + " balls");
            }else{
                System.out.println(players[playing].getPlayerName()+": BT"+ players[playing].getBolt());
            }
        }else{
            players[(playing+1)%3].giveBalls(balls.get(0));
            players[(playing+2)%3].giveBalls(balls.get(1));
            players[playing].giveBalls(roundPoints-(balls.get(0)+balls.get(1)));
            System.out.println(players[(playing+1)%3].getPlayerName()+": "+ players[(playing+1)%3].getTotalBalls() + " balls");
            System.out.println(players[(playing+2)%3].getPlayerName()+": "+ players[(playing+2)%3].getTotalBalls() + " balls");
            System.out.println(players[playing].getPlayerName()+": "+ players[playing].getTotalBalls() + " balls");
        }
        for(int i = 0; i < 3; i++){
            players[i].resetPoints();
        }

    }

    private void beginRound() {
        int pos = checkPlayingPlayerPos();
        for(int i = 0; i < 3; i++){
            players[i].checkForCombination();
        }
        while(!players[pos].getPlayerHand().isEmpty()){

            for(int i = pos; i < pos+3; i++){
                players[i%3].showCards();
                System.out.println(players[i%3].getPlayerName()+", Choose a card to play: ");
                int option = scanner.nextInt();
                players[i%3].throwACard(option,i-pos, tableCards);
                tableCards.showCards();
            }
            pos = (tableCards.checkWinner()+pos)%3;
            players[pos].givePoints(tableCards.getTotalPoints());
            tableCards.clearCards();
            if(players[pos].getPlayerHand().isEmpty()){
                players[pos].givePoints(10);
            }
        }
    }

    private int checkPlayingPlayerPos() {
        for(int i = 0; i < 3; i++){
            if(players[i].isPlaying()){
                return i;
            }
        }
        return 0;
    }

    private void printPlayingCard() {
        System.out.println("Playing card: "+ stack.getLastCard().getCardNumber()+" "+stack.getLastCard().getCardSign());
    }

    private void setCardsValue() {
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < players[i].getPlayerHand().size(); j++){
                if(players[i].getPlayerHand().get(j).getSignSortOrder() == players[i].getHighSignOrder()){
                    players[i].getPlayerHand().get(j).setHighValue(true);
                }
                players[i].getPlayerHand().get(j).setCardValue();
            }
        }
    }

    private boolean checkPlayingCardIsJack() {
        return stack.getLastCard().getCardNumber().equals("J");
    }

    public void giveLastCards(){
        boolean isJack = checkPlayingCardIsJack();
        if(isJack){
            for(int i = 0; i < 3; i++){
                players[i].setHighSignOrder(stack.getLastCard().getSignSortOrder());
            }
            players[((round + 1) % 3)].giveCardsFromStack(stack.giveCards(3));
            players[(round + 2) % 3].giveCardsFromStack(stack.giveCards(3));
            players[round % 3].giveCardsFromStack(stack.giveCards(3));
            players[((round + 1) % 3)].setPlaying(true);
        }else {
            for (int i = round; i < round+3; i++) {
                if (players[(i+1)%3].firstCircle(stack.getLastCard())) {
                    players[(i+1)%3].setPlaying(true);
                    for (int j = 0; j < 3; j++) {
                        players[j].setHighSignOrder(stack.getLastCard().getSignSortOrder());
                    }
                    players[(i+1)%3].giveCardsFromStack(stack.giveCards(1));
                    if ((round + 1) % 3 == (i+1)%3) {
                        players[(round + 1) % 3].giveCardsFromStack(stack.giveCards(2));
                    } else {
                        players[(round + 1) % 3].giveCardsFromStack(stack.giveCards(3));
                    }
                    if ((round + 2) % 3 == (i+1)%3) {
                        players[(round + 2) % 3].giveCardsFromStack(stack.giveCards(2));
                    } else {
                        players[(round + 2) % 3].giveCardsFromStack(stack.giveCards(3));
                    }
                    if ((round % 3) == (i+1)%3) {
                        players[round % 3].giveCardsFromStack(stack.giveCards(2));
                    } else {
                        players[round % 3].giveCardsFromStack(stack.giveCards(3));
                    }
                    break;
                } else {
                    if (i == round+2) {
                        for (int j = round; j < round+3; j++) {
                            String response = players[(j+1)%3].secondCircle(stack.getLastCard());
                            if (response.equals(Consts.CARDS_SIGNS_NAMES[0])) {
                                for (int k = 0; k < 3; k++) {
                                    players[k].setHighSignOrder(0);
                                }
                                players[(j+1)%3].setPlaying(true);
                                break;
                            } else if (response.equals(Consts.CARDS_SIGNS_NAMES[1])) {
                                for (int k = 0; k < 3; k++) {
                                    players[k].setHighSignOrder(1);
                                }
                                players[(j+1)%3].setPlaying(true);
                                break;
                            } else if (response.equals(Consts.CARDS_SIGNS_NAMES[2])) {
                                for (int k = 0; k < 3; k++) {
                                    players[k].setHighSignOrder(2);
                                }
                                players[(j+1)%3].setPlaying(true);
                                break;
                            } else if (response.equals(Consts.CARDS_SIGNS_NAMES[3])) {
                                for (int k = 0; k < 3; k++) {
                                    players[k].setHighSignOrder(3);
                                }
                                players[(j+1)%3].setPlaying(true);
                                break;
                            }
                        }
                        players[round % 3].giveCardsFromStack(stack.giveCards(1));
                        players[((round + 1) % 3)].giveCardsFromStack(stack.giveCards(3));
                        players[(round + 2) % 3].giveCardsFromStack(stack.giveCards(3));
                        players[round % 3].giveCardsFromStack(stack.giveCards(2));
                    }
                }
            }
        }
        //sorting cards
        for(int i = 0; i < 3; i++){
            players[i].sortCards();
        }
        //showing the cards
        for(int i = 0; i < 3; i++){
            players[i].showCards();
        }
    }


    private void giveFirstCards(){
        //give 3 cards to each player
        for(int i = round; i < round+3; i++){
            players[(i+1)%3].giveCardsFromStack(stack.giveCards(3));
        }

        //give 2 more cards to each player
        for(int i = round; i < round+3; i++){
            players[(i+1)%3].giveCardsFromStack(stack.giveCards(2));
        }

        //sorting cards
        for(int i = 0; i < 3; i++){
            players[i].sortCards();
        }
        //showing the cards
        for(int i = 0; i < 3; i++){
            players[i].showCards();
        }
    }
    private int pointsToBalls(int points){
        return Math.round(points%10);
    }
}
