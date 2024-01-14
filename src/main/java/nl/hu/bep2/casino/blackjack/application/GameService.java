package nl.hu.bep2.casino.blackjack.application;
import jakarta.transaction.Transactional;
import nl.hu.bep2.casino.blackjack.domain.Game;
import nl.hu.bep2.casino.blackjack.domain.State;
import nl.hu.bep2.casino.chips.application.ChipsService;
import org.springframework.stereotype.Service;

import static nl.hu.bep2.casino.blackjack.domain.State.*;

@Transactional
@Service
public class GameService {
//    private final GameRepository gameRepository;
    private final ChipsService chipsService;


    public GameService(ChipsService chipsService) { // GameRepository gameRepository,
//        this.gameRepository = gameRepository;
        this.chipsService = chipsService;
    }

    // Start the game and then save the result of the first round of cards.
    public Game startGame(String username, Long bet) { //MAKE DTO
        this.chipsService.withdrawChips(username, bet);
        Game game = Game.create();
        game.start(bet);
//        this.gameRepository.save(game);

        depositWinningChips(game, username);

        return game;
    }

    public Game hit(String username, Long id) {
        Game game = Game.create(); //findGameById(id);
        game.hit();
//        this.gameRepository.save(game);

        depositWinningChips(game, username);
        return game;
    }

    public Game stand(String username, Long id) {
        Game game = Game.create(); //findGameById(id);
        game.stand(game.getDealer());

//        this.gameRepository.save(game);

        depositWinningChips(game, username);
        return game;
    }

    public Game doubleHit(String username, Long id) {
        Game game = Game.create(); //findGameById(id);
        this.chipsService.withdrawChips(username, game.getBet());
        game.setBet(game.getBet()*2);
        game.doubleHit();
//        this.gameRepository.save(game);

        depositWinningChips(game, username);
        return game;
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

//    private Game findGameById(Long id) {
//        return this.gameRepository
//                .findById(id)
//                .orElse(Game.create());
//    }
}
