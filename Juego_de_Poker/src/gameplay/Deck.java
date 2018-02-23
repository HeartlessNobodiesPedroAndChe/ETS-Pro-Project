package gameplay;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @version 0.1 Early
 * @author Becerra Gutiérrez, Jesús Daniel
 * @author Suárez Delgado, Yared
 * @author Núñez Delgado, Eleazar
 * @author Borges Santamaría, Pedro
 */
public class Deck {

    private static ArrayList<ArrayList<Integer>> deck = new ArrayList<>();
    private static String[][] test_handhold;

    public Deck() {
        this.deck = load_deck();
        
        /* - - - Everything below is Test - - - */
        // Main deck
        System.out.println(watch_deck(deck));
        
        // Dealing cards
        System.out.println(Arrays.deepToString(deal_cards()));
        
        // Modified Deck
        System.out.println(watch_deck(deck));
    }

    /**
     * This method sets values to the attribute deck once Deck Object is
     * initializated.
     * Values setted into deck are Integer
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
     * This method will show our array of cards
     * @param deck as <code>ArrayList "ArrayList "Integer" "</code>
     * @return 
     */
    private static String watch_deck(ArrayList<ArrayList<Integer>> deck) {
        return Arrays.deepToString(deck.toArray());
    }
    
    /**
     * This method will give every player 5 random cards
     * @return Card Types as <code>String[Handhold card number][0=Suit, 1=Card number]</code>
     */
    public static String[][] deal_cards() {
        String[][] handhold_cards = new String[5][2];
        
        // Since we are playing 5 Card Draw Poker we just need to do this loop 5 times
        for (int i = 0; i < 5; i++) {
            
            // We get random suit (0 - 3 index)
            String suit = Integer.toString( (int)(Math.random() * 3) );
            
            // Then we set that suit with real value
            switch( Integer.parseInt(suit) ) {
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
            
            // Same code as above (0 - 12 index)
            String card = Integer.toString( (int)(Math.random() * 12) );
            
            // And setting real value
            switch( Integer.parseInt(card) + 1 ) {
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
                    handhold_cards[i][1] = card;
            }
            
            // Removing dealed card from Main Deck
            deck.get(Integer.parseInt(suit)).remove(Integer.parseInt(card));
        }
        return handhold_cards;
    }
    
}
