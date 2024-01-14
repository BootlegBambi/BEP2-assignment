package nl.hu.bep2.casino.blackjack.domain;

import java.io.Serializable;

public enum Rank implements Serializable {
    ACE("Ace", 11),
    TWO("Two", 2),
    THREE("Three", 3),
    FOUR("Four", 4),
    FIVE("Five", 5),
    SIX("Six", 6),
    SEVEN("Seven", 7),
    EIGHT("Eight", 8),
    NINE("Nine", 9),
    TEN("Ten", 10),
    KING("King", 10),
    QUEEN("Queen", 10),
    JACK("Jack", 10);

    private String name;
    private int value;

    Rank(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

}
