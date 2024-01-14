package nl.hu.bep2.casino.blackjack.domain;

public class Dealer implements Player {
    private Hand hand;

    public Dealer(Hand hand) {
        this.hand = hand;
    }

    @Override
    public int handScore() {
        return 0;
    }

    @Override
    public Hand getHand() {
        return null;
    }
}
