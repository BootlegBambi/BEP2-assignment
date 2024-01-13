package nl.hu.bep2.casino.blackjack.domain;

public class Game {
    private UserPlayer userPlayer;
    private Dealer dealer;
    private State state;
    private Deck deck;


    public Game(UserPlayer userPlayer, Dealer dealer, State state, Deck deck) {
        this.userPlayer = userPlayer;
        this.dealer = dealer;
        this.state = state;
        this.deck = deck;
    }



}
