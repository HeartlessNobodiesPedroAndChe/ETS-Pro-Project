package gameplay;

import java.util.Scanner;
import java.util.Random;

/**
 * @version 0.1 Early
 * @author Becerra Gutiérrez, Jesús Daniel
 * @author Suárez Delgado, Yared
 * @author Núñez Delgado, Eleazar
 * @author Borges Santamaría, Pedro
 */
public class Game extends Deck{
    
    private boolean inGame = false;
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
     * @param players Array of Players (need to be initializated)
     */
    public Game(Player... players) {
        Scanner input = new Scanner(System.in);
        Deck deck  = new Deck();
        this.players = players;
        
        for (int i = 0; i < players.length; i++) {
            System.out.print("Player Nº " + (i+1) + ", write your name: ");
            players[i].setName(input.nextLine());
            players[i].setHandhold_cards(deck.deal_cards());
        }
        
        inGame = true;
        set_dealer();
    }
    
    /**
     * This constructor gets a <b>Virtual Deck</b> and starts the game.<br>
     * <ul>
     * <li>Asks for every Player's name</li>
     * <li>Give 5 cards to every player</li>
     * <li>Sets a random Dealer</li>
     * </ul>
     * @param deck The <b>Virtual Deck</b> to play with.
     * @param players Array of Players (need to be initializated) (They can be just Players separeted by commas)
     */
    public Game(Deck deck, Player... players) {
        Scanner input = new Scanner(System.in);
        this.players = players;
        
        for (int i = 0; i < players.length; i++) {
            System.out.print("Player Nº " + (i+1) + ", write your name: ");
            players[i].setName(input.nextLine());
            players[i].setHandhold_cards(deck.deal_cards());
        }
        
        inGame = true;
        set_dealer();
    }
    
    /**
     * - - - - TEST METHOD - - - -
     */
    public void game_start() {
        
        while(inGame) {
            
        }
        
    }
    
    /**
     * This method sets a random dealer for the game to start.<br>
     * Sets <code>isDealer = true;</code> and <code>isPlaying = true;</code>
     * @see Random
     */
    public void set_dealer() {
        Random randomGenerator = new Random();
        int random = randomGenerator.nextInt(players.length);
        players[random].setDealer();
        players[random].setPlaying();
    }
    
}
