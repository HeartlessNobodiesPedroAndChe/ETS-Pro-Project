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

    private ArrayList<ArrayList<Integer>> deck = new ArrayList<>();

    public Deck() {
        this.deck = load_deck();
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
        
        for (int i = 0; i <= 4; i++) {
            // First of all we set 4 ArrayLists (each for Suit in the deck)
            deck_loaded.add( new ArrayList() );
            for (int j = 0; j <= 13; j++) {
                // Then we set in every Suit ( deck_loaded.get(i) ) the value ( *.add(j) )
                deck_loaded.get(i).add(j);
            }
        }
        
        return deck_loaded;
    }
    
    /**
     * 
     * @param deck as <code>ArrayList "ArrayList "Integer" "</code>
     * @return 
     */
    private static String watch_deck(ArrayList<ArrayList<Integer>> deck) {
        return Arrays.deepToString(deck.toArray());
    }

}
