package gameplay;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

/**
 * @version 0.1 Early
 * @author Becerra Gutiérrez, Jesús Daniel
 * @author Suárez Delgado, Yared
 * @author Núñez Delgado, Eleazar
 * @author Borges Santamaría, Pedro
 */
public class Game extends Deck {

    private boolean inGame = false;
    private double bet = 0;
    private double max_bet = 0;
    private double small_blind = 0;
    private final double big_blind = small_blind * 2;
    private int playingPlayerIndex;
    private Player[] players;
    private Deck deck;

    /**
     * This constructor basically starts a game.<br>
     * <ul>
     * <li>Initializes <code>Deck Object</code></li>
     * <li>Asks for every Player's name</li>
     * <li>Give 5 cards to every player</li>
     * <li>Sets a random Dealer</li>
     * </ul>
     *
     * @param players Array of Players (need to be initializated)
     */
    public Game(Player... players) {
        Scanner input = new Scanner(System.in);
        deck = new Deck();
        this.players = players;

        for (int i = 0; i < players.length; i++) {
            System.out.print("Player Nº " + (i + 1) + ", write your name: ");
            players[i].setName(input.nextLine());
            players[i].setHandhold_cards(deck.deal_cards());
        }

        setInGame();
        playingPlayerIndex = set_dealer();
    }

    /**
     * This constructor gets a <b>Virtual Deck</b> and starts the game.<br>
     * <ul>
     * <li>Asks for every Player's name</li>
     * <li>Give 5 cards to every player</li>
     * <li>Sets a random Dealer</li>
     * </ul>
     *
     * @param deck The <b>Virtual Deck</b> to play with.
     * @param players Array of Players (need to be initializated) (They can be
     * just Players separeted by commas)
     */
    public Game(Deck deck, Player... players) {
        Scanner input = new Scanner(System.in);
        this.players = players;

        for (int i = 0; i < players.length; i++) {
            System.out.print("Player Nº " + (i + 1) + ", write your name: ");
            players[i].setName(input.nextLine());
            players[i].setHandhold_cards(deck.deal_cards());
        }

        setInGame();
        playingPlayerIndex = set_dealer();
    }

    /**
     * - - - - TEST METHOD - - - -
     */
    public void game_start() {

        while (inGame) {
            // The gameplay is based on two rounds
            for (int i = 0; i < 2; i++) {
                
                for (Player player : players) {
                    // Player
                    System.out.println("\nPlayer '" + players[playingPlayerIndex].getName() + "', it's your turn:");
                    changeHand(players[playingPlayerIndex]);

                    // Change to next Player
                    next_player();
                }
                
            }
            setInGame();
        }

    }
    
    /**
     * This method check if playingPlayerIndex is higher than players length.<br>
     * If it is, it restarts to the very first player so we make sure every player plays.
     */
    private void next_player() {
        if (playingPlayerIndex + 1 > players.length - 1) {
            playingPlayerIndex = 0;
        } else {
            ++playingPlayerIndex;
        }
    }
    
    /**
     * This method lets you replace the cards in the Player Object if chosen to.
     * @param p Player Object to manipulate.
     * @return p Player Object with changed values.
     * @see Player
     * @see ArrayList
     */
    public Player changeHand (Player p) {
        ArrayList <Integer> change = new ArrayList<>();
        boolean ok = false;
        int value;
        String answer = "";
        Scanner scan = new Scanner(System.in);
        System.out.println("These are your cards:");
        System.out.println(p.showPlayerCards());
        System.out.print("Do you want to replace cards? (y/n): ");
        //This loop ensures that a valid input is entered.
        do {
            answer = scan.nextLine();
            if (answer.equals("y") || answer.equals("n")) {
                ok = true;
            } else {
                System.out.print("You have to introduce 'y' or 'n': ");
            }
            
        } while (ok == false);
        
        
        if (answer.equals("y")) {
            System.out.print("Which ones do you want to replace? [Enter '0' when you are done]: ");
            //This loop will gather the values of the cards to replace.
            do {
                value = scan.nextInt();
                if (value != 0 && value <= 5) {
                    change.add(value - 1);
                }

                if (change.size() == 5) {
                    break;
                }

            } while (value != 0);
        //Cards will be replaced by calling a few methods and then it will return the edited Object.
            p.setHandhold_cards(change_cards(p.getHandhold_cards(), change));
            System.out.println("\nThis is your new hand, " + p.getName());
            System.out.println(p.showPlayerCards());
        }
        System.out.println("\nGood Luck!\n");
        return p;
    }
    
    /**
     * This method sets a random dealer for the game to start and returns its
     * index.<br>
     * Sets <code>isDealer = true;</code> and <code>isPlaying = true;</code>
     *
     * @return index as <code>Integer</code> setting player[index] as dealer.
     * @see Random
     */
    public int set_dealer() {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(players.length - 1);
        players[index].setDealer();
        players[index].setPlaying();
        System.out.println("\nThe dealer is " + players[index].getName());
        return index;
    }

    /**
     * Simple method to change inGame to its negative.<br>
     * For example:<br> {@code if inGame == true; inGame = false;}<br>
     * {@code if inGame == false; inGame = true;}
     */
    public void setInGame() {
        inGame = !inGame;
    }

    /**
     * Simple method to manually change boolean <code>inGame</code>.
     *
     * @param inGame The boolean to change Game's inGame
     */
    public void setManualInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public void setBet(double bet) {
        this.bet = bet;
    }

    public void setMax_bet(double max_bet) {
        this.max_bet = max_bet;
    }

    public void setSmall_blind(double small_blind) {
        this.small_blind = small_blind;
    }

    public boolean isInGame() {
        return inGame;
    }

    public double getBet() {
        return bet;
    }

    public double getMax_bet() {
        return max_bet;
    }

    public double getSmall_blind() {
        return small_blind;
    }

    public double getBig_blind() {
        return big_blind;
    }

}
