package nl.hu.bep2.casino.blackjack.presentation.DTO;

import nl.hu.bep2.casino.blackjack.domain.Game;
import nl.hu.bep2.casino.blackjack.domain.Hand;
import nl.hu.bep2.casino.blackjack.domain.State;

public class GameViewDTO {
    private Hand userPlayerHand;
    private Hand dealerHand;
    private State state;

    public GameViewDTO(Game game) {
        this.userPlayerHand = game.getUserPlayer().getHand();
        this.dealerHand = game.getDealer().getHand();
        this.state = game.getState();
    }

    public Hand getUserPlayerHand() {
        return userPlayerHand;
    }

    public Hand getDealerHand() {
        return dealerHand;
    }

    public State getState() {
        return state;
    }
}