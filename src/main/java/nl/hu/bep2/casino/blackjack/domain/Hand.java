package nl.hu.bep2.casino.blackjack.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Hand implements Serializable {
    private List<Card> cardList = new ArrayList<>();

    public Hand() {
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
    }

    public void addCard(Card card) {
        this.cardList.add(card);
    }
}
