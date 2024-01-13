package nl.hu.bep2.casino.blackjack.domain;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private List<Card> cardList;

    private Deck(List<Card> cardList) {
        this.cardList = cardList;
    }
    public static Deck full() {
        List<Card> fullDeck = new ArrayList<>();

        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                Card card = new Card(rank, suit);
                fullDeck.add(card);
            }
        }

        return new Deck(fullDeck);
    }
}
