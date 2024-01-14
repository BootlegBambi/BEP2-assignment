package nl.hu.bep2.casino.blackjack.domain.exception;

public class GameEndedException extends RuntimeException {
    public GameEndedException(String message) {
        super(message);
    }
}