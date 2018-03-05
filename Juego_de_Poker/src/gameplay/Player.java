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
    
    public void matchHands(){
        int[][] handhold = Deck.parseArray(handhold_cards);
        handhold = reorderHandhold_cards(handhold);
        String Play = "";
        boolean Royal = false;
        int count = 0, aux = 0, suit = 0;
        if(handhold[0][1] == 0){
            setScoreString(0,handhold[0][1],0);
        }else{
        setScoreString(0,handhold[4][1],0);
        }
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
                if("Three of a Kind".equals(searchSames(handhold,i,aux))){
                    setScoreString(6,aux,Compare(handhold,aux));
                }
                break;
            case 2:
                setScoreString(3,aux,Integer.MIN_VALUE);
                if("Pair".equals(searchSames(handhold,i,aux)))
                    setScoreString(6,aux,Compare(handhold,aux));
                break;
            case 3: 
                setScoreString(7,aux,0);
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
        if(count == 4){
            setScoreString(5,handhold[4][1],0);
        }
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
        int count = 0, colorcount = 0;
        String Play ="";
        for (int j = 1; j < handhold.length-1; j++) {
            if(handhold[j][1]+1 == handhold[j+1][1]){
                count++;
                if (handhold[j][0] == handhold[j+1][0] && suit == handhold[j][0]) {
                    colorcount++;
                }
            }
        }
        if(count==3){
            if(colorcount == 3){
                setScoreString(9,handhold[0][1],Integer.MIN_VALUE);
            }
            setScoreString(4,handhold[0][1],Integer.MIN_VALUE);
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
    public int getScore(int i){
        return score[i];
    }
    public String[] getScoreString(){
        return ScoreString;
    }
    public String getScoreString(int i){
        return ScoreString[i];
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
   
    public void setScore(int[] score){
        this.score = score;
    }
    public void setScoreString(int hand, int firstvalue, int secondvalue){
        String[] score_ = new String[3];
        
        
        switch(firstvalue){
            case 0:
                score_[1] = "Ace";
                break;
            case 10:
                score_[1] = "Jack";
                break;
            case 11:
                score_[1] = "Queen";
                break;
            case 12:
                score_[1] = "King";
                break;
            default:
                score_[1] = String.valueOf(firstvalue+1);
        }
        switch(secondvalue){
            case 0:
                score_[2] = "Ace";
                break;
            case 10:
                score_[2] = "Jack";
                break;
            case 11:
                score_[2] = "Queen";
                break;
            case 12:
                score_[2] = "King";
                break;
            default:
                score_[2] = String.valueOf(secondvalue+1);
        }
        switch(hand){
            case 0:
                score_[0] = "High Card";
                break;
            case 1:
                score_[0] = "Pair";
                break;
            case 2:
                score_[0] = "Two Pairs";
                break;
            case 3:
                score_[0] = "Three of a Kind";
                break;
            case 4:
                score_[0] = "Straight";
                break;
            case 5:
                score_[0] = "Flush";
                break;
            case 6:
                score_[0] = "Full";
                break;
            case 7:
                score_[0] = "Poker";
                break;
            case 8:
                score_[0] = "Straight Flush";
                break;
            case 9:
                score_[0] = "Royal Straight";
                break;
        }
        this.ScoreString = score_;
        setScore(score_);
    }
    
    public void setScore(String[] ScoreString){
        int[] score_ = new int [3];
        switch(ScoreString[0]){
            case "High Card":
                score_[0] = 0;
                switch(ScoreString[1]){
                    case "Ace":
                        score_[1] = 13;
                        break;
                    case "Jack":
                        score_[1] = 10;
                        break;
                    case "Queen":
                        score_[1] = 11;
                        break;
                    case "King":
                        score_[1] = 12;
                        break;
                    default:
                        score_[1] = Integer.valueOf(ScoreString[1]);
                }
                 switch(ScoreString[2]){
                    case "Ace":
                        score_[2] = 13;
                        break;
                    case "Jack":
                        score_[2] = 10;
                        break;
                    case "Queen":
                        score_[2] = 11;
                        break;
                    case "King":
                        score_[2] = 12;
                        break;
                    default:
                        score_[2] = Integer.valueOf(ScoreString[2]);
                }
                 break;
            case "Pair":
                score_[0] = 1;
                switch(ScoreString[1]){
                    case "Ace":
                        score_[1] = 13;
                        break;
                    case "Jack":
                        score_[1] = 10;
                        break;
                    case "Queen":
                        score_[1] = 11;
                        break;
                    case "King":
                        score_[1] = 12;
                        break;
                    default:
                        score_[1] = Integer.valueOf(ScoreString[1]);
                }
                 switch(ScoreString[2]){
                    case "Ace":
                        score_[2] = 13;
                        break;
                    case "Jack":
                        score_[2] = 10;
                        break;
                    case "Queen":
                        score_[2] = 11;
                        break;
                    case "King":
                        score_[2] = 12;
                        break;
                    default:
                        score_[2] = Integer.valueOf(ScoreString[2]);
                }
                break;

            case "Two Pairs":
                score_[0] = 2;
                switch(ScoreString[1]){
                    case "Ace":
                        score_[1] = 13;
                        break;
                    case "Jack":
                        score_[1] = 10;
                        break;
                    case "Queen":
                        score_[1] = 11;
                        break;
                    case "King":
                        score_[1] = 12;
                        break;
                    default:
                        score_[1] = Integer.valueOf(ScoreString[1]);
                }
                 switch(ScoreString[2]){
                    case "Ace":
                        score_[2] = 13;
                        break;
                    case "Jack":
                        score_[2] = 10;
                        break;
                    case "Queen":
                        score_[2] = 11;
                        break;
                    case "King":
                        score_[2] = 12;
                        break;
                    default:
                        score_[2] = Integer.valueOf(ScoreString[2]);
                }
                break;
            case "Three of a Kind":
                score_[0] = 3;
               switch(ScoreString[1]){
                    case "Ace":
                        score_[1] = 13;
                        break;
                    case "Jack":
                        score_[1] = 10;
                        break;
                    case "Queen":
                        score_[1] = 11;
                        break;
                    case "King":
                        score_[1] = 12;
                        break;
                    default:
                        score_[1] = Integer.valueOf(ScoreString[1]);
                }
                 switch(ScoreString[2]){
                    case "Ace":
                        score_[2] = 13;
                        break;
                    case "Jack":
                        score_[2] = 10;
                        break;
                    case "Queen":
                        score_[2] = 11;
                        break;
                    case "King":
                        score_[2] = 12;
                        break;
                    default:
                        score_[2] = Integer.valueOf(ScoreString[2]);
                }
                break;
            case "Straight":
                score_[0] = 4;
                if("Ace".equals(score_[1])){
                    score_[1] = 13;
                }else{
                    score_[1] = Integer.parseInt(ScoreString[1]);
                }
                score_[2] = Integer.parseInt(ScoreString[1]);
                break;
            case "Flush":
                score_[0] = 5;
               switch(ScoreString[1]){
                    case "Ace":
                        score_[1] = 13;
                        break;
                    case "Jack":
                        score_[1] = 10;
                        break;
                    case "Queen":
                        score_[1] = 11;
                        break;
                    case "King":
                        score_[1] = 12;
                        break;
                    default:
                        score_[1] = Integer.valueOf(ScoreString[1]);
                }
                 switch(ScoreString[2]){
                    case "Ace":
                        score_[2] = 13;
                        break;
                    case "Jack":
                        score_[2] = 10;
                        break;
                    case "Queen":
                        score_[2] = 11;
                        break;
                    case "King":
                        score_[2] = 12;
                        break;
                    default:
                        score_[2] = Integer.valueOf(ScoreString[2]);
                }
                break;
            case "Full":
                score_[0] = 6;
                switch(ScoreString[1]){
                    case "Ace":
                        score_[1] = 13;
                        break;
                    case "Jack":
                        score_[1] = 10;
                        break;
                    case "Queen":
                        score_[1] = 11;
                        break;
                    case "King":
                        score_[1] = 12;
                        break;
                    default:
                        score_[1] = Integer.valueOf(ScoreString[1]);
                }
                 switch(ScoreString[2]){
                    case "Ace":
                        score_[2] = 13;
                        break;
                    case "Jack":
                        score_[2] = 10;
                        break;
                    case "Queen":
                        score_[2] = 11;
                        break;
                    case "King":
                        score_[2] = 12;
                        break;
                    default:
                        score_[2] = Integer.valueOf(ScoreString[2]);
                }
                break;
            case "Poker":
                score_[0] = 7;
               switch(ScoreString[1]){
                    case "Ace":
                        score_[1] = 13;
                        break;
                    case "Jack":
                        score_[1] = 10;
                        break;
                    case "Queen":
                        score_[1] = 11;
                        break;
                    case "King":
                        score_[1] = 12;
                        break;
                    default:
                        score_[1] = Integer.valueOf(ScoreString[1]);
                }
                 switch(ScoreString[2]){
                    case "Ace":
                        score_[2] = 13;
                        break;
                    case "Jack":
                        score_[2] = 10;
                        break;
                    case "Queen":
                        score_[2] = 11;
                        break;
                    case "King":
                        score_[2] = 12;
                        break;
                    default:
                        score_[2] = Integer.valueOf(ScoreString[2]);
                }
                break;
            case "Straight Flush":
                score_[0] = 8;
                switch(ScoreString[1]){
                    case "Ace":
                        score_[1] = 13;
                        break;
                    case "Jack":
                        score_[1] = 10;
                        break;
                    case "Queen":
                        score_[1] = 11;
                        break;
                    case "King":
                        score_[1] = 12;
                        break;
                    default:
                        score_[1] = Integer.valueOf(ScoreString[1]);
                }
                 switch(ScoreString[2]){
                    case "Ace":
                        score_[2] = 13;
                        break;
                    case "Jack":
                        score_[2] = 10;
                        break;
                    case "Queen":
                        score_[2] = 11;
                        break;
                    case "King":
                        score_[2] = 12;
                        break;
                    default:
                        score_[2] = Integer.valueOf(ScoreString[2]);
                }
                break;
            case "Royal Straight":
                score_[0] = 9;
               switch(ScoreString[1]){
                    case "Ace":
                        score_[1] = 13;
                        break;
                    case "Jack":
                        score_[1] = 10;
                        break;
                    case "Queen":
                        score_[1] = 11;
                        break;
                    case "King":
                        score_[1] = 12;
                        break;
                    default:
                        score_[1] = Integer.valueOf(ScoreString[1]);
                }
                 switch(ScoreString[2]){
                    case "Ace":
                        score_[2] = 13;
                        break;
                    case "Jack":
                        score_[2] = 10;
                        break;
                    case "Queen":
                        score_[2] = 11;
                        break;
                    case "King":
                        score_[2] = 12;
                        break;
                    default:
                        score_[2] = Integer.valueOf(ScoreString[2]);
                }
                break;
        }
        this.score = score_;
    }

    public boolean getIsDealer() {
        return isDealer;
    }
    
}
