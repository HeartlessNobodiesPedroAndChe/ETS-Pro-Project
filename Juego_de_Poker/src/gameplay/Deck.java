package gameplay;

import java.util.ArrayList;

/**
 *
 * @version 0.1 Early
 * @author Becerra Gutiérrez, Jesús Daniel
 * @author Suárez Delgado, Yared
 * @author Núñez Delgado, Eleazar
 * @author Borges Santamaría, Pedro
 */
public class Deck {

    private ArrayList<ArrayList<Integer>> deck = new ArrayList<>();

    public Deck() {
        // We load a new Deck
        deck = load_deck();
    }

    /**
     * This method sets values to the attribute deck once Deck Object is initializated. <br>
     * Values setted into deck are Integer.
     * @return deck_loaded as <code>ArrayList[][]</code>
     */
    private static ArrayList<ArrayList<Integer>> load_deck() {
        ArrayList<ArrayList<Integer>> deck_loaded = new ArrayList<>();
        
        for (int i = 0; i < 4; i++) {
            // First of all we set 4 ArrayLists (each for Suit in the deck)
            deck_loaded.add( new ArrayList() );
            for (int j = 0; j < 13; j++) {
                // Then we set in every Suit ( deck_loaded.get(i) ) the value ( *.add(j) )
                deck_loaded.get(i).add(j);
            }
        }
        
        return deck_loaded;
    }
    
    /**
     * This method will give 5 random cards to the player.
     * @return Card Types as <code>String[Handhold card number][0=Suit, 1=Card number]</code>
     */
    public String[][] deal_cards() {
        String[][] handhold_cards = new String[5][2];
        
        // Since we are playing 5 Card Draw Poker we just need to do this loop 5 times
        for (int i = 0; i < 5; i++) {
            
            // We get random suit (0 - number of suits index)
            int suit = (int)( Math.random() * deck.size() );
            
            // Then we set that suit with real value
            switch( suit ) {
                    case 0:
                        handhold_cards[i][0] = "Clubs";
                        break;
                    case 1:
                        handhold_cards[i][0] = "Spades";
                        break;
                    case 2:
                        handhold_cards[i][0] = "Hearts";
                        break;
                    case 3:
                        handhold_cards[i][0] = "Diamonds";
                        break;
            }
            
            // Same code as above (0 - Number of cards index)
            int card = (int)( Math.random() * deck.get(suit).size() );
            
            // And setting real value
            switch( card + 1 ) {
                case 1:
                    handhold_cards[i][1] = "Ace";
                    break;
                case 11:
                    handhold_cards[i][1] = "Jack";
                    break;
                case 12:
                    handhold_cards[i][1] = "Queen";
                    break;
                case 13:
                    handhold_cards[i][1] = "King";
                    break;
                // Default will get any other value (2 - 10)
                default:
                    handhold_cards[i][1] = Integer.toString(card + 1);
            }
            
            // Removing dealed card from Main Deck
            deck.get(suit).remove(card);
        }
        return handhold_cards;
    }

    public ArrayList<ArrayList<Integer>> getDeck() {
        return deck;
    }
    
}
