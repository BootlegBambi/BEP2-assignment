package nl.hu.bep2.casino.blackjack.application;

import jakarta.transaction.Transactional;
import nl.hu.bep2.casino.blackjack.data.GameEntity;
import nl.hu.bep2.casino.blackjack.data.GameRepository;
import nl.hu.bep2.casino.blackjack.domain.Game;
import nl.hu.bep2.casino.blackjack.domain.State;
import nl.hu.bep2.casino.blackjack.domain.exception.GameEndedException;
import nl.hu.bep2.casino.chips.application.ChipsService;
import nl.hu.bep2.casino.security.application.UserService;
import nl.hu.bep2.casino.security.domain.User;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

import static nl.hu.bep2.casino.blackjack.domain.State.*;

@Transactional
@Service
public class GameService {
    private final GameRepository gameRepository;
    private final ChipsService chipsService;
    private final UserService userService;


    public GameService(UserService userService, ChipsService chipsService, GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        this.chipsService = chipsService;
        this.userService = userService;
    }

    // Start the game and then save the result of the first round of cards.
    public Game startGame(String username, Long bet) {
        this.chipsService.withdrawChips(username, bet);
        Game game = Game.create();
        User user = userService.loadUserByUsername(username);
        game.start(bet);

        this.gameRepository.save(new GameEntity(game, user));
        depositWinningChips(game, username);
        return game;
    }


    public Game hit(String username, Long id) {
        return takeAction(username, id, game -> {
            validateActiveGame(game);
            game.hit();
        });
    }

    public Game stand(String username, Long id) {
        return takeAction(username, id, game -> {
            validateActiveGame(game);
            game.stand(game.getUserPlayer());
        });
    }

    public Game surrender(String username, Long id) {
        return takeAction(username, id, game -> {
            validateActiveGame(game);
            game.surrender(game.getUserPlayer());
        });
    }

    public Game doubleHit(String username, Long id) {
        return takeAction(username, id, game -> {
            validateActiveGame(game);
            this.chipsService.withdrawChips(username, game.getBet());
            game.setBet(game.getBet() * 2);
            game.doubleHit();
        });
    }

    // Works like the action delegate in NET, takes a function input to be used
    private Game takeAction(String username, Long id, Consumer<Game> action) {
        User user = userService.loadUserByUsername(username);
        GameEntity gameEntity = findGameById(user, id);
        Game game = gameEntity.getGame();

        action.accept(game);

        this.gameRepository.save(gameEntity);
        depositWinningChips(game, username);
        return game;
    }

    // Throws a custom GameEndedException if the game was already over.
    private void validateActiveGame(Game game) {
        if (game.getState() == BUST || game.getState() == WON || game.getState() == LOST
                || game.getState() == BLACKJACK || game.getState() == PUSH || game.getState() == SURRENDERED) {
            throw new GameEndedException("Can't act on a game that has ended.");
        }
    }


    // Deposit the chips to the user account, do nothing if the player lost.
    private void depositWinningChips(Game game, String username) {
        double multiplier = getMultiplier(game.getState());
        if (multiplier > 0) {
            this.chipsService.depositChips(username, (long) (game.getBet() * multiplier));
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

    private GameEntity findGameById(User user, Long id) {
        return this.gameRepository
                .findById(id)
                .orElseThrow();
    }

}
