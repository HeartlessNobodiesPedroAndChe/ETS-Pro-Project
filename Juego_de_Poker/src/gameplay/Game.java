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
public class Game {
    
    private boolean inGame = false;
    private Player[] players;
    
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
        players[random].setIsDealer(true);
        players[random].setPlaying();
    }
    
}
