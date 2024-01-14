package nl.hu.bep2.casino.blackjack.presentation.DTO;

import jakarta.validation.constraints.Positive;

public class StartGameDTO {
    @Positive
    public Long bet;
}