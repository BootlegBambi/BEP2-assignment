package nl.hu.bep2.casino.blackjack.application;
import nl.hu.bep2.casino.blackjack.data.GameRepository;
import nl.hu.bep2.casino.blackjack.domain.Game;
import nl.hu.bep2.casino.blackjack.domain.State;
import nl.hu.bep2.casino.chips.application.ChipsService;
import nl.hu.bep2.casino.security.application.UserService;

import static nl.hu.bep2.casino.blackjack.domain.State.*;


public class GameService {
    private final GameRepository gameRepository;
    private final UserService userService;
    private final ChipsService chipsService;


    public GameService(GameRepository gameRepository, UserService userService, ChipsService chipsService) {
        this.gameRepository = gameRepository;
        this.userService = userService;
        this.chipsService = chipsService;
    }

    // Start the game and then save the result of the first round of cards.
    public Game startGame(String username, Long bet) {
        this.chipsService.withdrawChips(username, bet);
        Game game = Game.create();
        game.start(bet);
        this.gameRepository.save(game);

        depositWinningChips(game, username, bet);

        return game;
    }

    // Deposit the chips to the user account, do nothing if the player lost.
    private void depositWinningChips(Game game, String username, Long bet) {
        double multiplier = getMultiplier(game.getState());
        if (multiplier > 0) {
            this.chipsService.depositChips(username, (long) (bet * multiplier));
        }
    }

    // Decides the right multiplier according to the gamestate.
    private double getMultiplier(State state) {
        double multiplier = 0;
        if (state == BLACKJACK) {
            multiplier = 1.5;
        }
        if (state == PUSH) {
            multiplier = 1;
        }
        if (state == SURRENDERED) {
            multiplier = 0.5;
        }
        if (state == WON) {
            multiplier = 2;
        }
        return multiplier;
    }

    public void hit(String username) {
    }

    public void stand(String username) {
    }

    public void doubleBet(String username, Long bet) {
        this.chipsService.withdrawChips(username, bet);
        hit(username);
    }

    public Game findGame(String username) {
        return this.findGameByUsername(username);
    }

    private Game findGameByUsername(String username) {
        return this.gameRepository
                .findByUsername(username)
                .orElse(Game.create());
    }
}
