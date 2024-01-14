package nl.hu.bep2.casino.blackjack.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck implements Serializable {
    private final List<Card> cardList;

    private Deck(List<Card> cardList) {
        this.cardList = cardList;
    }

    // Create a new full deck by going through all possible suit/rank card combinations.
    public static Deck full() {
        List<Card> fullDeck = new ArrayList<>();

        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                Card card = new Card(rank, suit);
                fullDeck.add(card);
            }
        }
        Collections.shuffle(fullDeck);
        return new Deck(fullDeck);
    }

    // Get the first card out of the deck and remove it.
    public Card getCardAndDiscard() {
        return this.cardList.remove(0);
    }
}
