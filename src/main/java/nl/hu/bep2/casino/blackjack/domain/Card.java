package nl.hu.bep2.casino.blackjack.domain;

import java.io.Serializable;

public class Card implements Serializable {
    private final Rank rank;
    private final Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }
}
