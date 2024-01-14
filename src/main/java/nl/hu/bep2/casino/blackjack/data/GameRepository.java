package nl.hu.bep2.casino.blackjack.data;

import jakarta.persistence.*;
import nl.hu.bep2.casino.blackjack.domain.Game;
import nl.hu.bep2.casino.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByUsername(String username);
}
