package org.entity;

import java.util.ArrayList;
import java.util.List;

public class TableCards {

    private List<Card> cards;

    private boolean highCardPlaced;
    private int totalPoints;

    public TableCards(){
        highCardPlaced = false;
        totalPoints = 0;
        cards = new ArrayList<Card>();
    }

    public void putCard(Card card){
        cards.add(card);
        if(getFirstCard().isHighValue()){
            highCardPlaced = true;
        }
    }

    public int checkWinner(){
        totalPoints = 0;
        int winner = 0;
        int winner_pos = 0;
        totalPoints+=cards.get(0).getCardValue();
        for(int i = 1; i < 3; i++){
            totalPoints+=cards.get(i).getCardValue();
            if(cards.get(i).isHighValue()){
                if(cards.get(winner_pos).isHighValue()){
                    if(cards.get(i).getCardValue()>cards.get(winner_pos).getCardValue()){
                        winner = cards.get(i).getCardValue();
                        winner_pos = i;
                    }
                }else{
                    winner = cards.get(i).getCardValue();
                    winner_pos = i;
                }
            }else{
                if(!cards.get(winner_pos).isHighValue()){
                    if(cards.get(i).getCardValue()>cards.get(winner_pos).getCardValue()){
                        winner = cards.get(i).getCardValue();
                        winner_pos = i;
                    }
                }
            }
        }
        return winner_pos;
    }

    public Card getFirstCard() {
        return cards.get(0);
    }

    public boolean isHighCardPlaced() {
        return highCardPlaced;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void clearCards() {
        cards.clear();
    }

    public void showCards() {
        for (Card card : cards) {
            System.out.print(card.getCardNumber() + " " + card.getCardSign() + ", ");
        }
        System.out.println();
        System.out.println();
    }
}
