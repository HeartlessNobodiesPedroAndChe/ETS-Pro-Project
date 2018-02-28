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
    private int score;
    private static Deck deck = new Deck();
    
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
    public int[][] reorderHandhold_cards (int[][] handhold){
        int aux = 0, exchange = 0;
        boolean organized = false;
        do{
        for (int i = 0; i < handhold.length; i++) {
            for (int j = 1; j < handhold.length; j++) {
                if(handhold[i][1] < handhold[j][1]){
                    aux = handhold[i][1];
                    handhold[i][1] = handhold[j][1];
                    handhold[j][1] = aux;
                    exchange++;
                }
            }
            if (exchange == 0) {
                organized = true;
            }
        }
        exchange = 0;
        }while(!organized);
        System.out.print("Cards: ");
        for (int i = 0; i < handhold.length; i++) {
            System.out.print(handhold[i][1]+" ");
        }
        return handhold;
    }
    public String matchHands(){
        int[][] handhold = deck.parseArray(handhold_cards);
        handhold = reorderHandhold_cards(handhold);
        String Play = "";
        int count = 0, aux = 0;
        for (int i = 0; i < handhold.length;i++) {
            if(i+1 < handhold.length){
                if(handhold[i][1]== handhold[i+1][1]){
                    Play = Player.this.searchSames(handhold,i);
                }
                if(handhold[i][1]+1 == handhold[i+1][1]){
                    Play = searchFlush(handhold,i);
                }
            }
        }
        return Play;
    }
    public String searchSames(int[][] handhold,int i){
        int count = 0;
        String Play ="";
        int aux = handhold[i][1];
        for (int j = 1; j < handhold.length; j++) {
            if(aux == handhold[j][1]){
                count++;
            }
        }
        switch(count){
            case 0:
                Play = "Pair";
                if(Play.equals(searchSames(handhold,i,aux)))
                    Play = "Two Pairs";
                if("Trio".equals(searchSames(handhold,i,aux)))
                    Play = "Full House";
                break;
            case 1:
                Play = "Three of a Kind";
                if("Pair".equals(searchSames(handhold,i,aux)))
                    Play = "Full House";
                break;
            case 2: 
                Play = "Poker";
                break;
        }
        return Play;
    }
    
    public String searchSames(int [][] handhold,int i, int firstpair){
        int count = 0;
        String Play = "";
        for (int j = 1; j < handhold.length; j++) {
            if(firstpair == handhold[j][1]){
                count++;
            }
        }
        switch(count){
            case 0:
                Play = "Pair";
                break;
            case 1:
                Play = "Three of a Kind";
                break;
            default: 
                Play = "";
                break;
        }
        return Play;
    }
    
    public String searchFlush(int[][] handhold, int i){
        return "hola";
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
