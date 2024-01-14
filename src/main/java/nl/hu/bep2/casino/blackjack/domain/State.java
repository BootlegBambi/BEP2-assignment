package nl.hu.bep2.casino.blackjack.domain;

import java.io.Serializable;

public enum State implements Serializable {

    PLAYING,
    BUST,
    LOST,
    SURRENDERED,
    PUSH,
    BLACKJACK,
    WON

}
