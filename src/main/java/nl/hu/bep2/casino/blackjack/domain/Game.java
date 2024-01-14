package nl.hu.bep2.casino.blackjack.domain;

public class Game {
    private UserPlayer userPlayer;
    private Dealer dealer;
    private State state;
    private Deck deck;
    private Long bet;


    public Game(UserPlayer userPlayer, Dealer dealer, Deck deck) {
        this.userPlayer = userPlayer;
        this.dealer = dealer;
        this.deck = deck;
    }

    public Game() {
    }

    // Create a new game with a full deck & empty hands
    public static Game create() {
        Hand playerHand = new Hand();
        Hand dealerHand = new Hand();
        Deck deck = Deck.full();

        UserPlayer player = new UserPlayer(playerHand);
        Dealer dealer = new Dealer(dealerHand);

        return new Game(player, dealer, deck);
    }

    public void start(Long bet) {
        this.bet = bet;
        initialCardsDealt();
        initialResult();
    }

    // Gives the user and dealer each 2 cards to start the game with
    private void initialCardsDealt() {
        Hand userHand = this.userPlayer.getHand();
        Hand dealerHand = this.dealer.getHand();

        userHand.addCard(deck.getCardAndDiscard());
        dealerHand.addCard(deck.getCardAndDiscard());

        userHand.addCard(deck.getCardAndDiscard());
        dealerHand.addCard(deck.getCardAndDiscard());
    }


    // Adds a new card to the hand, it will hit again if the score is below 17.
    public void dealerHit() {
        Hand playerHand = dealer.getHand();

        if ((dealer.handScore() < 17)) {
            playerHand.addCard(deck.getCardAndDiscard());
            this.dealerHit();
        } else {
            this.stand(dealer);
        }
    }

    // Adds a new card to the hand
    public void hit() {
        Hand playerHand = userPlayer.getHand();
        playerHand.addCard(deck.getCardAndDiscard());
        checkBust();
    }


    // If a dealer stands it means the game is over, if a player stands it's the dealers turn to hit.
    public void stand(Player player) {
        if (player instanceof Dealer) {
            checkFinalResult();
        }
        else {
            dealerHit();
        }
    }

    // Adds a new card to the hand and then stands for the userPlayer, it's the dealers turn to hit.
    public void doubleHit() {
        Hand playerHand = userPlayer.getHand();

        playerHand.addCard(deck.getCardAndDiscard());
        checkBust();
        dealerHit();
    }

    // Checks if there was a blackjack in anyone's first hand and sets the state accordingly
    private void initialResult() {
        int userPlayerScore = userPlayer.handScore();
        int dealerScore = dealer.handScore();

        if (userPlayerScore == 21 && dealerScore == 21) {
            state = State.PUSH;
        } else if (userPlayerScore == 21) {
            state = State.BLACKJACK;
        } else {
            state = State.PLAYING;
        }
    }

    // Check we didn't go over 24 with our hit
    private void checkBust() {
        int userPlayerScore = userPlayer.handScore();

        if (userPlayerScore > 24) {
            state = State.BUST;
        }
    }

    // After both userPLayer and dealer stand on their cards, it's time to see who won.
    private void checkFinalResult() {
        int userPlayerScore = userPlayer.handScore();
        int dealerScore = dealer.handScore();

        if (userPlayerScore == 21 && dealerScore == 21) {
            state = State.PUSH;
        } else if (userPlayerScore == 21) {
            state = State.BLACKJACK;
        } else if (dealerScore > 24) {
            state = State.WON;
        } else if (dealerScore > userPlayerScore) {
            state = State.LOST;
        } else if (userPlayerScore > dealerScore) {
            state = State.WON;
        } else state = State.PUSH;
    }

    public State getState() {
        return state;
    }

    public Long getBet() {
        return bet;
    }

    public void setBet(Long bet) {
        this.bet *= 2;
    }

    public UserPlayer getUserPlayer() {
        return userPlayer;
    }

    public Dealer getDealer() {
        return dealer;
    }

}
