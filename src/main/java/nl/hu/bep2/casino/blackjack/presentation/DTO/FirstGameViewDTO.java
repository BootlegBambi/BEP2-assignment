package nl.hu.bep2.casino.blackjack.presentation.DTO;

import nl.hu.bep2.casino.blackjack.domain.Card;
import nl.hu.bep2.casino.blackjack.domain.Game;
import nl.hu.bep2.casino.blackjack.domain.Hand;
import nl.hu.bep2.casino.blackjack.domain.State;

import java.util.List;

public class FirstGameViewDTO {
    private Hand userPlayerHand;
    private Hand dealerHand;
    private State state;


    public FirstGameViewDTO(Game game) {
        List<Card> realHand = game.getDealer().getHand().getCardList();
        this.dealerHand = new Hand();

        this.userPlayerHand = game.getUserPlayer().getHand();
        this.dealerHand.addCard(realHand.get(0));
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