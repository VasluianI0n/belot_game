package org.entity;

public class Card {

    //signs: ♠, ♥︎, ♦︎, ♣︎
    private String cardSign;
    private int signSortOrder;

    //numbers: 9,10,J,Q,K,A
    private String cardNumber;
    private int cardSortOrder;


    private int cardValue;
    private boolean isHighValue;


    public Card(String cardSign, String cardNumber, int signSortOrder, int cardSortOrder){
        this.cardSign = cardSign;
        this.cardNumber = cardNumber;
        this.signSortOrder = signSortOrder;
        this.cardSortOrder = cardSortOrder;
    }


    public int getCardValue() {
        return cardValue;
    }

    public void setCardValue() {
        if(isHighValue){
            switch (getCardNumber()) {
                case "9" -> cardValue = 14;
                case "10" -> cardValue = 10;
                case "J" -> cardValue = 20;
                case "Q" -> cardValue = 3;
                case "K" -> cardValue = 4;
                case "A" -> cardValue = 11;
            }
        }else{
            switch (getCardNumber()) {
                case "9" -> cardValue = 0;
                case "10" -> cardValue = 10;
                case "J" -> cardValue = 2;
                case "Q" -> cardValue = 3;
                case "K" -> cardValue = 4;
                case "A" -> cardValue = 11;
            }
        }
    }

    public boolean isHighValue() {
        return isHighValue;
    }

    public void setHighValue(boolean highValue) {
        isHighValue = highValue;
    }

    public String getCardSign() {
        return cardSign;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getSignSortOrder() {
        return signSortOrder;
    }

    public int getCardSortOrder() {
        return cardSortOrder;
    }


}
