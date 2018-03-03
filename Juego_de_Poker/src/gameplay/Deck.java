package gameplay;

import java.util.ArrayList;
import java.util.Random;

/**
 * Useful <b>Virtual Deck</b> that is loaded once you create {@code Deck Object}
 * or loaded by deck ({@literal ArrayList<ArrayList<Integer>>}) argument.<br><br>
 * <b>Virtual Deck</b> basic methods includes <b>loading cards</b> (new Virtual Deck),
 * <b>changing them</b> and <b>dealing them</b>.
 * 
 * @version 0.1 Early
 * @author Becerra GutiÃ©rrez, JesÃºs Daniel
 * @author SuÃ¡rez Delgado, Yared
 * @author NÃºÃ±ez Delgado, Eleazar
 * @author Borges SantamarÃ­a, Pedro
 * @see ArrayList
 * @see Player
 */
public class Deck {
    
    /**
     * The {@code Deck Object} is based on a 2D ArrayList, first one for Suits
     * and second one for Cards in Suit.
     */
    private ArrayList<ArrayList<Integer>> deck = new ArrayList<>();
    
    /**
     * Automatic loading deck Constructor for <b>Virtual Deck</b>.
     */
    public Deck() {
        // We load a new Deck
        deck = load_deck();
    }
    
    /**
     * This method set deck argument as <b>Virtual Deck</b>.
     * @param deck The deck you want to play with as
     * <code>{@literal ArrayList<ArrayList<Integer>>}</code>
     */
    public Deck(ArrayList<ArrayList<Integer>> deck) {
        // We load the deck argument
        this.deck = deck;
    }

    /**
     * This method sets values to the attribute deck once Deck Object is initializated.<br>
     * Values setted into deck are Integer.
     * @return deck_loaded as <code>{@literal ArrayList<ArrayList<Integer>>}</code>
     */
    private ArrayList<ArrayList<Integer>> load_deck() {
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
        Random randomGenerator = new Random();
        
        // Since we are playing 5 Card Draw Poker we just need to do this loop 5 times
        for (String[] handhold_card : handhold_cards) {
            // We get random suit (0 - number of suits index)
            int suit = randomGenerator.nextInt(deck.size());
            handhold_card[0] = setSuit(suit);
            // Same code as above (0 - Number of cards index)
            int card = randomGenerator.nextInt(deck.get(suit).size());
            handhold_card[1] = setCard(suit, card);
            // Removing dealed card from Main Deck
            deck.get(suit).remove(card);
        }
        return handhold_cards;
    }
    
    /**
     * This method will return a new set of cards for the Player.
     * @param handhold_cards Player's cards to change
     * @param indexes The indexes of <code>handhold_cards</code> as
     * <code>{@literal ArrayList<Integer>}</code>
     * @return new handhold_cards as <code>String[][]</code>
     */
    public String[][] change_cards(String[][] handhold_cards, ArrayList<Integer> indexes) {
        
        Random randomGenerator = new Random();
        
        for (int i = 0; i < indexes.size(); i++) {
            
            // We set random number for suit
            int suit = randomGenerator.nextInt(deck.size());
            // And we give that value to the Player's cards
            handhold_cards[indexes.get(i)][0] = setSuit(suit);
            
            // We set random number for card
            int card = randomGenerator.nextInt(deck.get(suit).size());
            // And we give that value to the Player's cards
            handhold_cards[indexes.get(i)][1] = setCard(suit, card);
            
            // Then we remove that card from the Deck
            deck.get(suit).remove(card);
        }
        
        return handhold_cards;
    }
    
    /**
     * This method will return a new set of cards for the Player.
     * @param handhold_cards Player's cards to change
     * @param indexes The indexes of <code>handhold_cards</code> as <code>int[]</code>
     * @return new handhold_cards as <code>String[][]</code>
     */
    public String[][] change_cards(String[][] handhold_cards, int... indexes) {
        
        Random randomGenerator = new Random();
        
        for (int i = 0; i < indexes.length; i++) {
            
            // We set random number for suit
            int suit = randomGenerator.nextInt(deck.size());
            // And we give that value to the Player's cards
            handhold_cards[indexes[i]][0] = setSuit(suit);
            
            // We set random number for card
            int card = randomGenerator.nextInt(deck.get(suit).size());
            // And we give that value to the Player's cards
            handhold_cards[indexes[i]][1] = setCard(suit, card);
            
            // Then we remove that card from the Deck
            deck.get(suit).remove(card);
        }
        
        return handhold_cards;
    }
    
    /**
     * This method will parse numbers into Suit and Card.<br>
     * It gets {@code handhold_cards[][]} as {@code String[][]}
     * and returns it as {@code int[][]}.
     * @param handhold_cards The array to change.
     * @return handhold_cards as <code>String[][]</code>
     */
    public static String[][] parseArray(int[][] handhold_cards) {
        String[][] parsed_cards = new String[handhold_cards.length][2];
        
        for (int i = 0; i < handhold_cards.length; i++) {
            
            // Then we set that suit with real value
            switch ( handhold_cards[i][0] ) {
                case 0:
                    parsed_cards[i][0] = "Clubs";
                    break;
                case 1:
                    parsed_cards[i][0] = "Spades";
                    break;
                case 2:
                    parsed_cards[i][0] = "Hearts";
                    break;
                case 3:
                    parsed_cards[i][0] = "Diamonds";
                    break;
            }
            
            // And setting real value
            switch ( handhold_cards[i][1] ) {
                case 0:
                    parsed_cards[i][1] = "Ace";
                    break;
                case 10:
                    parsed_cards[i][1] = "Jack";
                    break;
                case 11:
                    parsed_cards[i][1] = "Queen";
                    break;
                case 12:
                    parsed_cards[i][1] = "King";
                    break;
                // Default will get any other value (2 - 10)
                default:
                    parsed_cards[i][1] = Integer.toString(handhold_cards[i][1] + 1);
            }
        }
        return parsed_cards;
    }
    
    /**
     * This method will parse Suit and Card into Numbers.<br>
     * It gets {@code handhold_cards[][]} as {@code int[][]}
     * and returns it as {@code String[][]}.
     * @param handhold_cards The array to change.
     * @return handhold_cards as <code>String[][]</code>
     */
    public static int[][] parseArray(String[][] handhold_cards) {
        int[][] parsed_cards = new int[handhold_cards.length][2];
        
        // We get the length of handhold so we make sure we get every card
        for (int i = 0; i < handhold_cards.length; i++) {
            
            // We set suit number by suit String
            switch ( handhold_cards[i][0] ) {
                case "Clubs":
                    parsed_cards[i][0] = 0;
                    break;
                case "Spades":
                    parsed_cards[i][0] = 1;
                    break;
                case "Hearts":
                    parsed_cards[i][0] = 2;
                    break;
                case "Diamonds":
                    parsed_cards[i][0] = 3;
                    break;
            }
            
            // We set card number by card String
            switch ( handhold_cards[i][1] ) {
                case "Ace":
                    parsed_cards[i][1] = 0;
                    break;
                case "Jack":
                    parsed_cards[i][1] = 10;
                    break;
                case "Queen":
                    parsed_cards[i][1] = 11;
                    break;
                case "King":
                    parsed_cards[i][1] = 12;
                    break;
                default:
                    parsed_cards[i][1] = Integer.parseInt(handhold_cards[i][1]) - 1;
            }
            
        }
        return parsed_cards;
    }
    
    /**
     * Simple method to get Suit Name by Number.
     * @param suit The suit number as <code>Integer</code>
     * @return SuitName as <code>String</code>
     */
    private String setSuit(int suit) {
        String suitName = "";
        
        // Then we set that suit with real value
        switch (suit) {
            case 0:
                suitName = "Clubs";
                break;
            case 1:
                suitName = "Spades";
                break;
            case 2:
                suitName = "Hearts";
                break;
            case 3:
                suitName = "Diamonds";
                break;
        }
        return suitName;
    }
    
    /**
     * Simple method to get Card Name by Number.
     * @param suit The suit number as <code>Integer</code>
     * @return CardName as <code>String</code>
     */
    private String setCard(int suit, int card) {
        String CardName;

        switch ( deck.get(suit).get(card) ) {
            case 0:
                CardName = "Ace";
                break;
            case 10:
                CardName = "Jack";
                break;
            case 11:
                CardName = "Queen";
                break;
            case 12:
                CardName = "King";
                break;
            // Default will get any other value (2 - 10)
            default:
                CardName = Integer.toString(deck.get(suit).get(card) + 1);
        }
            
        return CardName;
    }
    
    /**
     * This method will return the entire ArrayList of the deck.<br>
     * Please, remember deck works like: {@literal ArrayList<ArrayList<Integer>>}.<br>
     * Where first ArrayList is Suit and the one inside is Card.
     * @return deck as <code>{@literal ArrayList<ArrayList<Integer>>}</code>
     */
    public ArrayList<ArrayList<Integer>> getDeck() {
        return deck;
    }
    
    @Override
    public String toString() {
        return deck.toString();
    }
    
}
