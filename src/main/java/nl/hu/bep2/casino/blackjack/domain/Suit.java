package nl.hu.bep2.casino.blackjack.domain;

import java.io.Serializable;

public enum Suit implements Serializable {
    CLUBS("Clubs"),
    DIAMONDS("Diamonds"),
    HEARTS("Hearts"),
    SPADES("Spades");

    Suit(String name) {
    }
}
