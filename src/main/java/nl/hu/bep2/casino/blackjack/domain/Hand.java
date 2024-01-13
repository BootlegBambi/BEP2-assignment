package nl.hu.bep2.casino.blackjack.domain;

import java.util.List;

public class Hand {
    private List<Card> cardList;

    public Hand(List<Card> cardList) {
        this.cardList = cardList;
    }
}
