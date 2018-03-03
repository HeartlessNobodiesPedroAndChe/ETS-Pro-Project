package gameplay;

import java.util.Arrays;

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
    private boolean isSmallBlind = false;
    private boolean isBigBlind = false;
    private int score;
    
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
        
        // We automatically reorder Player's hand
        handhold_cards = Deck.parseArray(reorderHandhold_cards(Deck.parseArray(handhold_cards)));
        
        // We get Card and Suit for every card in handhold array.
        for (String[] handhold_card : handhold_cards) {
          cards += "Card nº " + card_number + ": " + handhold_card[1] + " " + handhold_card[0] + "\n";
          card_number++;
        }
        
        return cards;
    }
    /**
     * HOW TO NAME POKER HANDS IN ENGLISH (RANKED FROM LOWEST TO HIGHEST):
     * CARTA ALTA: HIGH CARD
     * PAREJA: PAIR
     * DOBLE PAREJA: TWO PAIRS
     * TRIO: THREE OF A KIND
     * ESCALERA NORMAL: STRAIGHT
     * 5 CARTAS MISMO PALO: FLUSH
     * FULL: FULL HOUSE
     * POKER: POKER (FOUR OF A KIND)
     * ESCALERA DE COLOR: STRAIGHT FLUSH
     * ESCALERA REAL: ROYAL FLUSH
     */
    public int[][] reorderHandhold_cards (int[][] handhold){
        Arrays.sort(handhold, (int[] o1, int[] o2) -> Integer.compare(o1[1],o2[1]));
        return handhold;
    }
    
    public String matchHands(){
        int[][] handhold = Deck.parseArray(handhold_cards);
        handhold = reorderHandhold_cards(handhold);
        String Play = "";
        boolean Royal = false;
        int count = 0, aux = 0, suit = 0;
        for (int i = 0; i < handhold.length-1;i++) {
            if(handhold[i][1]== handhold[i+1][1]){
                Play = searchSames(handhold,i);
            }
            if(handhold[i][0] == handhold[i+1][0]){
                Play = searchFlush(handhold,i);
            }
        }
        if(handhold[0][1]+1 == handhold[1][1]){
            Play = searchStraight(handhold);
        }
        if(handhold[0][1]==0 && handhold[1][1] == 9){
            suit = handhold[0][0];
            if (handhold[0][0] == handhold[1][0]){
                Royal = true;
            }
            Play = searchRoyalStraight(handhold,Royal,suit);
        }
        return Play;
    }
    public String searchSames(int[][] handhold,int i){
        int count = 0;
        String Play ="";
        int aux = handhold[i][1];
        for (int j = 0; j < handhold.length; j++) {
            if(aux == handhold[j][1] && i!=j){
                count++;
            }
        }
        switch(count){
            case 1:
                Play = "Pair";
                if(Play.equals(searchSames(handhold,i,aux)))
                    Play = "Two Pairs";
                if("Trio".equals(searchSames(handhold,i,aux)))
                    Play = "Full House";
                break;
            case 2:
                Play = "Three of a Kind";
                if("Pair".equals(searchSames(handhold,i,aux)))
                    Play = "Full House";
                break;
            case 3: 
                Play = "Poker";
                break;
        }
        return Play;
    }
    
    public String searchSames(int [][] handhold,int i, int firstpair){
        int count = 0, aux = 0, k = 0;
        String Play;
        for (int j = 0; j < handhold.length-1; j++) {
            if(firstpair != handhold[j][1] && handhold[j+1][1] == handhold[j][1]){
                aux = handhold[j][1];
                k = j;
            }
        }
        for (int j = 0; j < handhold.length; j++) {
            if(aux == handhold[j][1] && k!=j){
                count++;
            }
        }
        switch(count){
            case 1:
                Play = "Pair";
                break;
            case 2:
                Play = "Three of a Kind";
                break;
            default: 
                Play = "";
                break;
        }
        return Play;
    }
    
    public String searchFlush(int[][] handhold, int i){
        int count = 0;
        String Play ="";
        int aux = handhold[i][0];
        for (int j = 0; j < handhold.length; j++) {
            if(aux == handhold[j][0] && i!=j){
                count++;
            }
        }
        if(count == 4)
            return "Flush";
        else
            return "";
    }
    
    public String searchStraight(int[][] handhold){
        int count = 0;
        String Play ="";
        for (int j = 0; j < handhold.length-1; j++) {
            if(handhold[j][1]+1 == handhold[j+1][1]){
                count++;
            }
            if (handhold[j][0] == handhold[j+1][0]) {
                count++;
            }
        }
        switch(count){
            case 4: 
                return "Straight";
            case 8: 
                return "Straight Flush";
            default: 
                return "";
        }
    }
    public String searchRoyalStraight(int[][] handhold, boolean Royal,int suit){
         int count = 0;
        String Play ="";
        for (int j = 1; j < handhold.length-1; j++) {
            if(handhold[j][1]+1 == handhold[j+1][1]){
                count++;
                if (handhold[j][0] == handhold[j+1][0] && suit == handhold[j][0]) {
                    count++;
                }
            }
        }
        switch(count){
            case 3:
                return "Straight";
            case 6:
                return "Royal Straight";
            default:
                return "";
        }
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
    
    /**
     * Simple method to change isPlaying to its negative.<br>
     * For example:<br>
     * {@code if isSmallBlind == true; isSmallBlind = false;}<br>
     * {@code if isSmallBlind == false; isSmallBlind = true;}
     */
    public void set_SmallBlind() {
        isSmallBlind = !isSmallBlind;
    }
    
    /**
     * Simple method to manually change boolean <code>isSmallBlind</code>.
     * @param isSmallBlind The boolean to change Player's isSmallBlind
     */
    public void setManualSmallBlind(boolean isSmallBlind) {
        this.isSmallBlind = isSmallBlind;
    }
    
    /**
     * Simple method to change isPlaying to its negative.<br>
     * For example:<br>
     * {@code if isBigBlind == true; isBigBlind = false;}<br>
     * {@code if isBigBlind == false; isBigBlind = true;}
     */
    public void set_BigBlind() {
        isBigBlind = !isBigBlind;
    }
    
    /**
     * Simple method to manually change boolean <code>isBigBlind</code>.
     * @param isDealer The boolean to change Player's isBigBlind
     */
    public void setManualBigBlind(boolean isDealer) {
        this.isBigBlind = isBigBlind;
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
    public int getScore(){
        return score;
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
    
    public void setScore(int score){
        this.score = score;
    }

    public boolean getIsDealer() {
        return isDealer;
    }
    
}
