package org.entity;

import org.Utils.Consts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Player {

    private List<Card> playerHand;
    private String cardsCombination;
    private int combinationValue;
    private String playerName;
    private int highSignOrder;
    private boolean dealer;
    private boolean playing;

    private int combinationBalls;

    private int totalPoints;

    private int totalBalls;

    private int numberOfBolts;
    public Player(String playerName){
        totalBalls = 0;
        numberOfBolts = 0;
        playerHand = new ArrayList<Card>();
        this.playerName = playerName;
    }

    public void giveCardsFromStack(List<Card> cardsFromStack){
        getPlayerHand().addAll(0, cardsFromStack);
    }

    public void showCards(){
        System.out.print(getPlayerName()+": ");
        for(int i = 0; i < getPlayerHand().size();i++){
            System.out.print(getPlayerHand().get(i).getCardNumber()+" "+getPlayerHand().get(i).getCardSign()+", ");
        }
        System.out.println();
        System.out.println();
    }

    public boolean isDealer() {
        return dealer;
    }

    public void setDealer(boolean dealer) {
        this.dealer = dealer;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public List<Card> getPlayerHand() {
        return playerHand;
    }

    public String getCardsCombination() {
        return cardsCombination;
    }

    public int getCombinationValue() {
        return combinationValue;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setCardsCombination(String cardsCombination) {
        this.cardsCombination = cardsCombination;
    }

    public void setCombinationValue(int combinationValue) {
        this.combinationValue = combinationValue;
    }

    public void sortCards(){
        for(int i = 0; i < getPlayerHand().size(); i++){
            for(int j = 0; j < getPlayerHand().size() - 1; j++){
                if(getPlayerHand().get(j).getSignSortOrder() > getPlayerHand().get(j+1).getSignSortOrder()){
                    Collections.swap(getPlayerHand(), j, j+1);
                }else if(getPlayerHand().get(j).getSignSortOrder() == getPlayerHand().get(j+1).getSignSortOrder()){
                    if(getPlayerHand().get(j).getCardSortOrder() > getPlayerHand().get(j+1).getCardSortOrder()){
                        Collections.swap(getPlayerHand(), j, j+1);
                    }
                }
            }
        }
    }

    public boolean firstCircle(Card lastCard){
        Scanner scanner = new Scanner(System.in);
        System.out.println(getPlayerName()+" choosing: PASS / PLAY (IN "+ Consts.CARDS_SIGNS_NAMES[lastCard.getSignSortOrder()] + ")");
        String option = scanner.nextLine();
        while((!option.equals("PASS"))&&(!option.equals("PLAY"))){
            System.out.println("Choose between available options: PASS / PLAY");
            option = scanner.nextLine();
        }
        if(option.equals("PLAY")){
            return true;
        }else{
            return false;
        }
    }
    public String secondCircle(Card lastCard){
        Scanner scanner = new Scanner(System.in);
        String[] cards_names = new String[3];
        int counter = 0;
        if(isPlaying()){
            System.out.print(getPlayerName()+" choosing: ");
            for(int i = 0; i < 4; i++){
                if(i != lastCard.getSignSortOrder()){
                    System.out.print(Consts.CARDS_SIGNS_NAMES[i] + " / ");
                    cards_names[counter] = Consts.CARDS_SIGNS_NAMES[i];
                    counter++;
                }
            }
            System.out.println();
            String option = scanner.nextLine();
            while((!option.equals(cards_names[0]))&&(!option.equals(cards_names[1]))&&(!option.equals(cards_names[2]))){
                System.out.print("Choose between available options: ");
                for(int i = 0; i < 3; i++){
                    System.out.print(cards_names[i]+" / ");
                }
                System.out.println();
                option = scanner.nextLine();
            }

                return option;

        }else{
            System.out.print(getPlayerName()+" choosing: PASS / ");
            for(int i = 0; i < 4; i++){
                if(i != lastCard.getSignSortOrder()){
                    System.out.print(Consts.CARDS_SIGNS_NAMES[i] + " / ");
                    cards_names[counter] = Consts.CARDS_SIGNS_NAMES[i];
                    counter++;
                }
            }
            System.out.println();
            String option = scanner.nextLine();
            while((!option.equals(cards_names[0]))&&(!option.equals(cards_names[1]))&&(!option.equals(cards_names[2]))&&(!option.equals("PASS"))){
                System.out.print("Choose between available options: PASS / ");
                for(int i = 0; i < 3; i++){
                    System.out.print(cards_names[i]+" / ");
                }
                System.out.println();
                option = scanner.nextLine();
            }

            return option;
        }
    }

    public int getHighSignOrder() {
        return highSignOrder;
    }

    public void setHighSignOrder(int highSignOrder) {
        this.highSignOrder = highSignOrder;
    }

    public void throwACard(int posOfCard, int posOfPlayer, TableCards tableCards){
        if(posOfPlayer == 0){
            if(!isPlaying()){
                if(getPlayerHand().get(posOfCard).isHighValue()){
                    if(tableCards.isHighCardPlaced()){
                        tableCards.putCard(getPlayerHand().get(posOfCard));
                        getPlayerHand().remove(posOfCard);
                    }else{
                        Scanner scanner = new Scanner(System.in);
                        int option = posOfCard;
                        while(getPlayerHand().get(option).isHighValue() && !tableCards.isHighCardPlaced()){
                            System.out.println("You are not able to put a high card first. Please choose another card");
                            option = scanner.nextInt();
                        }
                        tableCards.putCard(getPlayerHand().get(option));
                        getPlayerHand().remove(option);
                    }
                }else {
                    tableCards.putCard(getPlayerHand().get(posOfCard));
                    getPlayerHand().remove(posOfCard);
                }
            }else{
                tableCards.putCard(getPlayerHand().get(posOfCard));
                getPlayerHand().remove(posOfCard);
            }
        }else{
                if(!tableCards.getFirstCard().getCardSign().equals(getPlayerHand().get(posOfCard).getCardSign())){
                    if(checkForCardSign(tableCards.getFirstCard().getCardSign())){
                        Scanner scanner = new Scanner(System.in);
                        int option = posOfCard;
                        while(!tableCards.getFirstCard().getCardSign().equals(getPlayerHand().get(option).getCardSign())){
                            System.out.println("You have a card with the same sign. Choose one of them");
                            option = scanner.nextInt();
                        }
                        tableCards.putCard(getPlayerHand().get(option));
                        getPlayerHand().remove(option);
                    }else{
                        if(!getPlayerHand().get(posOfCard).isHighValue()){
                            if(hasHighValueCards()){
                                Scanner scanner = new Scanner(System.in);
                                int option = posOfCard;
                                while(!getPlayerHand().get(option).isHighValue()){
                                    System.out.println("When you don't have any of the same sign, use High Cards.");
                                    option = scanner.nextInt();
                                }
                                tableCards.putCard(getPlayerHand().get(option));
                                getPlayerHand().remove(option);
                            }else{
                                tableCards.putCard(getPlayerHand().get(posOfCard));
                                getPlayerHand().remove(posOfCard);
                            }
                        }else{
                            tableCards.putCard(getPlayerHand().get(posOfCard));
                            getPlayerHand().remove(posOfCard);
                        }
                    }
                }else{
                    tableCards.putCard(getPlayerHand().get(posOfCard));
                    getPlayerHand().remove(posOfCard);
                }
        }
    }

    private boolean hasHighValueCards() {
        for(int i = 0; i < getPlayerHand().size(); i++){
            if(getPlayerHand().get(i).isHighValue()){
                return true;
            }
        }
        return false;
    }

    private boolean checkForCardSign(String cardSign) {
        for(int i = 0; i < getPlayerHand().size(); i++){
            if(getPlayerHand().get(i).getCardSign().equals(cardSign)){
                return true;
            }
        }
        return false;
    }

    public void givePoints(int points) {
        totalPoints+=points;
        System.out.println("Given "+ points+ " to "+ getPlayerName());
    }

    public int getPoints(){
        return totalPoints;
    }

    public void checkForCombination(){
        List<Card> remainingCards = getPlayerHand();
        int totalCombinationValue = 0;
        int counter = 0;
        for(int i = 0; i < remainingCards.size()-1; i++){

        }
    }

    public void giveBolt() {
        numberOfBolts++;
        if(numberOfBolts == 3){
            totalBalls-=10;
            numberOfBolts = 0;
        }
    }

    public int getBolt(){
        return numberOfBolts;
    }

    public void giveBalls(Integer balls) {
        totalBalls+=balls;
    }

    public int getTotalBalls(){
        return totalBalls;
    }

    public void resetPoints(){
        totalPoints=0;
    }
}
