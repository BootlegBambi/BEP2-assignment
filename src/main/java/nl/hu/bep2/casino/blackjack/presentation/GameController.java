package nl.hu.bep2.casino.blackjack.presentation;

import nl.hu.bep2.casino.blackjack.application.GameService;
import nl.hu.bep2.casino.blackjack.domain.Game;
import nl.hu.bep2.casino.blackjack.domain.exception.GameEndedException;
import nl.hu.bep2.casino.blackjack.presentation.DTO.FirstGameViewDTO;
import nl.hu.bep2.casino.blackjack.presentation.DTO.GameViewDTO;
import nl.hu.bep2.casino.blackjack.presentation.DTO.StartGameDTO;
import nl.hu.bep2.casino.chips.domain.exception.NegativeNumberException;
import nl.hu.bep2.casino.security.domain.UserProfile;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/game")
public class GameController {
    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @PostMapping("/start")
    public FirstGameViewDTO start(Authentication authentication, @Validated @RequestBody StartGameDTO gameDTO) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();

        try {
            Game game = this.service.startGame(profile.getUsername(), gameDTO.bet);
            return new FirstGameViewDTO(game);
        } catch (NegativeNumberException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    @PostMapping("/hit/{id}")
    public GameViewDTO hit(Authentication authentication, @PathVariable Long id) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();

        try {
            Game game = this.service.hit(profile.getUsername(), id);
            return new GameViewDTO(game);
        } catch (NoSuchElementException | GameEndedException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    @PostMapping("/stand/{id}")
    public GameViewDTO stand(Authentication authentication, @PathVariable Long id) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();

        try {
            Game game = this.service.stand(profile.getUsername(), id);
            return new GameViewDTO(game);
        } catch (NoSuchElementException | GameEndedException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    @PostMapping("/doublehit/{id}")
    public GameViewDTO doubleHit(Authentication authentication, @PathVariable Long id) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();

        try {
            Game game = this.service.doubleHit(profile.getUsername(), id);
            return new GameViewDTO(game);
        } catch (NoSuchElementException | GameEndedException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    @PostMapping("/surrender/{id}")
    public GameViewDTO surrender(Authentication authentication, @PathVariable Long id) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();

        try {
            Game game = this.service.surrender(profile.getUsername(), id);
            return new GameViewDTO(game);
        } catch (NoSuchElementException | GameEndedException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }
}
