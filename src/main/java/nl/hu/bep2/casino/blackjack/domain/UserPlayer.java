package nl.hu.bep2.casino.blackjack.domain;

public class UserPlayer implements Player {
    private Hand hand;

    public UserPlayer(Hand hand) {
        this.hand = hand;
    }
}
