package gameplay;

import java.util.Arrays;

/**
 * Class to set players and manage Gameplay.
 * 
 * @author Becerra GutiÃ©rrez, JesÃºs Daniel
 * @author SuÃ¡rez Delgado, Yared
 * @author NÃºÃ±ez Delgado, Eleazar
 * @author Borges SantamarÃ­a, Pedro
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
    private int[] score;
    private String[] ScoreString;
    
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
          cards += "Card nÂº " + card_number + ": " + handhold_card[1] + " " + handhold_card[0] + "\n";
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
    
    public void matchHands(){
        int[][] handhold = Deck.parseArray(handhold_cards);
        handhold = reorderHandhold_cards(handhold);
        String Play = "";
        boolean Royal = false;
        int count = 0, aux = 0, suit = 0;
        for (int i = 0; i < handhold.length-1;i++) {
            if(handhold[i][1]== handhold[i+1][1]){
                searchSames(handhold,i);
            }
            if(handhold[i][0] == handhold[i+1][0]){
                searchFlush(handhold,i);
            }
        }
        if(handhold[0][1]+1 == handhold[1][1]){
            searchStraight(handhold);
        }
        if(handhold[0][1]==0 && handhold[1][1] == 9){
            suit = handhold[0][0];
            if (handhold[0][0] == handhold[1][0]){
                Royal = true;
            }
            searchRoyalStraight(handhold,Royal,suit);
        }
        setScoreString(0,handhold[4][1],0);
    }
    public void searchSames(int[][] handhold,int i){
        int count = 0;
        int[] score_ = new int[3];
        int Play;
        int aux = handhold[i][1];
        for (int j = 0; j < handhold.length; j++) {
            if(aux == handhold[j][1] && i!=j){
                count++;
            }
        }
        switch(count){
            case 1:
                setScoreString(1,aux,0);
                if("Pair".equals(searchSames(handhold,i,aux))){
                    setScoreString(2,aux,Compare(handhold, aux));
                }
                if("Trio".equals(searchSames(handhold,i,aux))){
                    setScoreString(6,aux,Compare(handhold,aux));
                }
                break;
            case 2:
                if("Pair".equals(searchSames(handhold,i,aux)))
                    setScoreString(6,aux,Compare(handhold,aux));
                break;
            case 3: 
                setScoreString(7,aux,Integer.MIN_VALUE);
                break;
        }
    }
    
    public int Compare(int[][] handhold, int firstpair){
        int aux = 0;
        for (int j = 0; j < handhold.length-1; j++) {
            if(firstpair != handhold[j][1] && handhold[j+1][1] == handhold[j][1]){
                aux = handhold[j][1];
            }
        }
        return aux;
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
    
    public void searchFlush(int[][] handhold, int i){
        int count = 0;
        String Play ="";
        int aux = handhold[i][0];
        for (int j = 0; j < handhold.length; j++) {
            if(aux == handhold[j][0] && i!=j){
                count++;
            }
        }
        if(count == 4)
            setScoreString(5,handhold[4][1],0);
    }
    
    public void searchStraight(int[][] handhold){
        int count = 0, colorcount = 0;
        String Play ="";
        for (int j = 0; j < handhold.length-1; j++) {
            if(handhold[j][1]+1 == handhold[j+1][1]){
                count++;
            }
            if (handhold[j][0] == handhold[j+1][0]) {
                colorcount++;
            }
        }
        if(count==4){
            if(colorcount == 4){
                setScoreString(8,handhold[4][1],Integer.MIN_VALUE);
            }
            setScoreString(4,handhold[4][1],Integer.MIN_VALUE);
        }
    }
    
    public void searchRoyalStraight(int[][] handhold, boolean Royal,int suit){
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
                setScoreString(4,handhold[0][1],Integer.MIN_VALUE);
                break;
            case 6:
                setScoreString(9,handhold[0][1],Integer.MIN_VALUE);
                break;
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
    public int[] getScore(){
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
    public void getScoreString(String[] ScoreString){
        this.ScoreString = ScoreString;
    }
    public void setScore(int[] score){
        this.score = score;
    }
    public void setScoreString(int hand, int firstvalue, int secondvalue){
        String[] score_ = new String[3];
        int scoret = 0;
        switch(hand){
            case 0:
                score_[0] = "High Card";
                score_[1] = String.valueOf(firstvalue);
                score_[2] = String.valueOf(secondvalue);
                break;
            case 1:
                score_[0] = "Pair";
                score_[1] = String.valueOf(firstvalue);
                score_[2] = String.valueOf(secondvalue);
                break;
            case 2:
                score_[0] = "Two Pairs";
                score_[1] = String.valueOf(firstvalue);
                score_[2] = String.valueOf(secondvalue);
                break;
            case 3:
                score_[0] = "Three of a Kind";
                score_[1] = String.valueOf(firstvalue);
                score_[2] = String.valueOf(secondvalue);
                break;
            case 4:
                score_[0] = "Straight";
                score_[1] = String.valueOf(firstvalue);
                score_[2] = String.valueOf(secondvalue);
                break;
            case 5:
                score_[0] = "Flush";
                score_[1] = String.valueOf(firstvalue);
                score_[2] = String.valueOf(secondvalue);
                break;
            case 6:
                score_[0] = "Full";
                score_[1] = String.valueOf(firstvalue);
                score_[2] = String.valueOf(secondvalue);
                break;
            case 7:
                score_[0] = "Poker";
                score_[1] = String.valueOf(firstvalue);
                score_[2] = String.valueOf(secondvalue);
                break;
            case 8:
                score_[0] = "Straight Flush";
                score_[1] = String.valueOf(firstvalue);
                score_[2] = String.valueOf(secondvalue);
                break;
            case 9:
                score_[0] = "Royal Straight";
                score_[1] = String.valueOf(firstvalue);
                score_[2] = String.valueOf(secondvalue);
                break;
        }
        this.ScoreString = score_;
    }
    
    public void setScore(String[] ScoreString){
        int[] score_ = new int [3];
        switch(ScoreString[0]){
            case "High Card":
                score_[0] = 0;
                if("0".equals(score_[1])){
                    score_[1] = 12;
                }else{
                    score_[1] = Integer.parseInt(ScoreString[1]+1);
                }
                score_[2] = Integer.parseInt(ScoreString[2]+1);
                break;
            case "Pair":
                score_[0] = 1;
                if("0".equals(score_[1])){
                    score_[1] = 12;
                }else{
                    score_[2] = Integer.parseInt(ScoreString[1]+1);
                }
                score_[2] = Integer.parseInt(ScoreString[2]+1);
                break;

            case "Two Pairs":
                score_[0] = 2;
                if("0".equals(score_[1])){
                    score_[1] = 12;
                }else{
                    score_[1] = Integer.parseInt(ScoreString[1]+1);
                }
                score_[2] = Integer.parseInt(ScoreString[1]+1);
                break;
            case "Three of a Kind":
                score_[0] = 3;
                if("0".equals(score_[1])){
                    score_[1] = 12;
                }else{
                    score_[1] = Integer.parseInt(ScoreString[1]+1);
                }
                score_[2] = Integer.parseInt(ScoreString[1]+1);
                break;
            case "Straight":
                score_[0] = 4;
                if("0".equals(score_[1])){
                    score_[1] = 12;
                }else{
                    score_[1] = Integer.parseInt(ScoreString[1]+1);
                }
                score_[2] = Integer.parseInt(ScoreString[1]+1);
                break;
            case "Flush":
                score_[0] = 5;
                if("0".equals(score_[1])){
                    score_[1] = 12;
                }else{
                    score_[1] = Integer.parseInt(ScoreString[1]+1);
                }
                score_[2] = Integer.parseInt(ScoreString[1]+1);
                break;
            case "Full":
                score_[0] = 6;
                if("0".equals(score_[1])){
                    score_[1] = 12;
                }else{
                    score_[1] = Integer.parseInt(ScoreString[1]+1);
                }
                score_[2] = Integer.parseInt(ScoreString[1]+1);
                break;
            case "Poker":
                score_[0] = 7;
                if("0".equals(score_[1])){
                    score_[1] = 12;
                }else{
                    score_[1] = Integer.parseInt(ScoreString[1]+1);
                }
                score_[2] = Integer.parseInt(ScoreString[1]+1);
                break;
            case "Straight Flush":
                score_[0] = 8;
                if("0".equals(score_[1])){
                    score_[1] = 12;
                }else{
                    score_[1] = Integer.parseInt(ScoreString[1]+1);
                }
                score_[2] = Integer.parseInt(ScoreString[1]+1);
                break;
            case "Royal Straight":
                score_[0] = 9;
                if("0".equals(score_[1])){
                    score_[1] = 12;
                }else{
                    score_[1] = Integer.parseInt(ScoreString[1]+1);
                }
                score_[2] = Integer.parseInt(ScoreString[1]+1);
                break;
        }
        this.score = score_;
    }

    public boolean getIsDealer() {
        return isDealer;
    }
    
}
