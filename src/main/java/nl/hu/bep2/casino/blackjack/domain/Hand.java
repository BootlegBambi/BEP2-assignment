package nl.hu.bep2.casino.blackjack.domain;

import java.util.List;

public class Hand {
    private List<Card> cardList;

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
