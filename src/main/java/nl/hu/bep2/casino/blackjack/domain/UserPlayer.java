package nl.hu.bep2.casino.blackjack.domain;

import java.io.Serializable;

public class UserPlayer implements Player, Serializable {
    private final Hand hand;

    public UserPlayer(Hand hand) {
        this.hand = hand;
    }

    @Override
    public Hand getHand() {
        return this.hand;
    }

    @Override
    // Calculates the score, will change value of ACE to 1 if 22 is reached.
    public int handScore() {
        int score = 0;
        for (Card card : this.hand.getCardList()) {
            score += card.getRank().getValue();
            if (card.getRank().getValue() == 11 && score > 21) {
                score -= 10;
            }
        }
        return score;
    }

}
