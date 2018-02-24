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
    private double money = 0;
    private String[][] handhold_cards;
    private boolean isPlaying;
    
    public Player(String name, double money, String[][] handhold_cards) {
        this.name = name;
        this.money = money;
        this.handhold_cards = handhold_cards;
        isPlaying = false;
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
    
}
