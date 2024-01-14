package nl.hu.bep2.casino.blackjack.data;

import jakarta.persistence.*;
import nl.hu.bep2.casino.blackjack.domain.Game;
import nl.hu.bep2.casino.security.domain.User;

import java.io.Serializable;

@Entity
@Table(name = "gameEntity")
public class GameEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    private Game game;

    @ManyToOne
    private User user;
}