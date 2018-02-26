package gameplay;

/**
 * Class to set players and manage Gameplay.
 * 
 * @author Becerra Gutiérrez, Jesús Daniel
 * @author Suárez Delgado, Yared
 * @author Núñez Delgado, Eleazar
 * @author Borges Santamaría, Pedro
 * @see Deck
 */
public class Player {
    
    // Variables
    private String name;
    private double money = 10;
    private String[][] handhold_cards;
    private boolean isDealer = false;
    private boolean isPlaying = false;
    
    /**
     * The empty constructor generates by default:<br>
     * {@code double money = 10;}<br>
     * {@code isDealer = false;}<br>
     * {@code isPlaying = false;}
     */
    public Player() {}
    
    /**
     * This constructor generates by default:<br>
     * {@code isDealer = false;}
     * {@code isPlaying = false;}
     * @param name The name of the Player
     * @param money The amount richness
     * @param handhold_cards The cards this Player owns
     */
    public Player(String name, double money, String[][] handhold_cards) {
        this.name = name;
        this.money = money;
        this.handhold_cards = handhold_cards;
    }
    
    /**
     * Show player's card with Suit and Number.<br>
     * This method is just for testing, will be deprecated ASAP.
     * @return Cheats as <code>String[][]</code>.
     */
    public String showPlayerCards() {
        String cards = "";
        int card_number = 1;
        
        // We get Card and Suit for every card in handhold array.
        for (String[] handhold_card : handhold_cards) {
          cards += "Card nº " + card_number + ": " + handhold_card[1] + " " + handhold_card[0] + "\n";
          card_number++;
        }
        
        return cards;
    }
    
    /**
     * Simple method to change isPlaying to its negative.<br>
     * For example:<br>
     * {@code if isPlaying == true; isPlaying = false;}<br>
     * {@code if isPlaying == false; isPlaying = true;}
     */
    public void setPlaying() {
        isPlaying = !isPlaying;
    }
    
    /**
     * Simple method to manually change boolean <code>isPlaying</code>.
     * @param isPlaying The boolean to change Payer's isPlaying
     */
    public void setManualPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }
    
    /**
     * Simple method to change isPlaying to its negative.<br>
     * For example:<br>
     * {@code if isDealer == true; isDealer = false;}<br>
     * {@code if isDealer == false; isDealer = true;}
     */
    public void setDealer() {
        isDealer = !isDealer;
    }
    
     /**
     * Simple method to manually change boolean <code>isDealer</code>.
     * @param isDealer The boolean to change Payer's isDealer
     */
    public void setManualDealer(boolean isDealer) {
        this.isDealer = isDealer;
    }
    
    public String getName() {
        return name;
    }

    public double getMoney() {
        return money;
    }

    public String[][] getHandhold_cards() {
        return handhold_cards;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setHandhold_cards(String[][] handhold_cards) {
        this.handhold_cards = handhold_cards;
    }

    public boolean getIsDealer() {
        return isDealer;
    }
    
}
