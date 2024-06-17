package org.entity;

import org.Utils.Consts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Stack {
    private List<Card> stackOfCards = new ArrayList<Card>();


    public Stack() {
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 6; j++){
                stackOfCards.add(new Card(Consts.CARDS_SIGNS[i], Consts.CARDS_NUMBERS[j], Consts.CARDS_SIGNS_SORT_ORDER[i],Consts.CARDS_NUMBERS_SORT_ORDER[j]));
            }
        }
    }

    public void printStackOfCards(){
        for(int i = 0; i < 24; i++){
            System.out.print(stackOfCards.get(i).getCardNumber()+" "+ stackOfCards.get(i).getCardSign()+", ");
        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
    }

    public void shuffle(){
        Collections.shuffle(stackOfCards);
    }

    public List<Card> giveCards(int numberOfCards){
        List<Card> playerCards = new ArrayList<Card>();
        for(int i = 0; i < numberOfCards; i++){
            playerCards.add(stackOfCards.get(i));
        }
        for(int i = 0; i < numberOfCards; i++){
            stackOfCards.remove(0);
        }
        return playerCards;
    }

    public Card getLastCard(){
        return stackOfCards.get(0);
    }
}
