package nl.hu.bep2.casino.blackjack.domain;

public class Game {
    private UserPlayer userPlayer;
    private Dealer dealer;
    private State state;
    private Deck deck;


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
        initialCardsDealt();
        checkInitialResult();
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

    // Checks if there was a blackjack and sets the state accordingly
    private void checkInitialResult() {
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

    public void stand(Player player) {
        Hand playerHand = player.getHand();
        playerHand.addCard(deck.getCardAndDiscard());

    }

    public void hit(Player player) {

    }

    public State getState() {
        return state;
    }
}
