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
     * Simple method to reorder the hand.
     *
     * @param handhold as <code>Integer[]</code>
     * @return Orderder <code>Integer[]</code> of Handhold_cards
     */
    public int[][] reorderHandhold_cards(int[][] handhold) {
        Arrays.sort(handhold, (int[] o1, int[] o2) -> Integer.compare(o1[1], o2[1]));
        return handhold;
    }

    /**
     *
     */
    public void matchHands() {
        int[][] handhold = Deck.parseArray(handhold_cards);
        handhold = reorderHandhold_cards(handhold);
        String Play = "";
        boolean Royal = false;
        int count, aux = 0, suit = 0;
        
        for (int i = 0; i < handhold.length - 1; i++) {
            if (handhold[i][1] == handhold[i + 1][1]) {
                searchSames(handhold, i);
            }
            if (handhold[i][0] == handhold[i + 1][0]) {
                searchFlush(handhold, i);
            }
        }
        
        if (handhold[0][1] + 1 == handhold[1][1]) {
            searchStraight(handhold);
        }
        
        if (handhold[0][1] == 0 && handhold[1][1] == 9) {
            suit = handhold[0][0];
            if (handhold[0][0] == handhold[1][0]) {
                Royal = true;
            }
            searchRoyalStraight(handhold, Royal, suit);
        }
        
        setScore(0, handhold[4][1], 0);
    }

    /**
     *
     * @param handhold
     * @param i
     */
    public void searchSames(int[][] handhold, int i) {
        int count = 0;
        int[] score_ = new int[3];
        int Play;
        int aux = handhold[i][1];
        
        for (int j = 0; j < handhold.length; j++) {
            if (aux == handhold[j][1] && i != j) {
                count++;
            }
        }

        switch (count) {
            case 1:
                setScore(1, aux, 0);
                if ("Pair".equals(searchSames(handhold, i, aux))) {
                    setScore(2, aux, Compare(handhold, aux));
                }
                if ("Trio".equals(searchSames(handhold, i, aux))) {
                    setScore(6, aux, Compare(handhold, aux));
                }
                break;
            case 2:
                if("Pair".equals(searchSames(handhold,i,aux)))
                    setScore(6,aux,Compare(handhold,aux));
                break;
            case 3:
                setScore(7, aux, Integer.MIN_VALUE);
                break;
        }
    }

    /**
     *
     * @param handhold
     * @param firstpair
     * @return
     */
    public int Compare(int[][] handhold, int firstpair) {
        int aux = 0;
        for (int j = 0; j < handhold.length - 1; j++) {
            if (firstpair != handhold[j][1] && handhold[j + 1][1] == handhold[j][1]) {
                aux = handhold[j][1];
            }
        }
        return aux;
    }

    /**
     *
     * @param handhold
     * @param i
     * @param firstpair
     * @return
     */
    public String searchSames(int[][] handhold, int i, int firstpair) {
        int count = 0, aux = 0, k = 0;
        String Play;
        for (int j = 0; j < handhold.length - 1; j++) {
            if (firstpair != handhold[j][1] && handhold[j + 1][1] == handhold[j][1]) {
                aux = handhold[j][1];
                k = j;
            }
        }
        for (int j = 0; j < handhold.length; j++) {
            if (aux == handhold[j][1] && k != j) {
                count++;
            }
        }
        switch (count) {
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

    /**
     *
     * @param handhold
     * @param i
     */
    public void searchFlush(int[][] handhold, int i) {
        int count = 0;
        String Play = "";
        int aux = handhold[i][0];
        for (int j = 0; j < handhold.length; j++) {
            if (aux == handhold[j][0] && i != j) {
                count++;
            }
        }
        if (count == 4) {
            setScore(5, handhold[4][1], 0);
        }
    }

    /**
     *
     * @param handhold
     */
    public void searchStraight(int[][] handhold) {
        int count = 0, colorcount = 0;
        String Play = "";
        for (int j = 0; j < handhold.length - 1; j++) {
            if (handhold[j][1] + 1 == handhold[j + 1][1]) {
                count++;
            }
            if (handhold[j][0] == handhold[j + 1][0]) {
                colorcount++;
            }
        }
        if (count == 4) {
            if (colorcount == 4) {
                setScore(8, handhold[4][1], Integer.MIN_VALUE);
            }
            setScore(4, handhold[4][1], Integer.MIN_VALUE);
        }
    }

    /**
     *
     * @param handhold
     * @param Royal
     * @param suit
     */
    public void searchRoyalStraight(int[][] handhold, boolean Royal, int suit) {
        int count = 0;
        String Play = "";
        for (int j = 1; j < handhold.length - 1; j++) {
            if (handhold[j][1] + 1 == handhold[j + 1][1]) {
                count++;
                if (handhold[j][0] == handhold[j + 1][0] && suit == handhold[j][0]) {
                    count++;
                }
            }
        }
        switch (count) {
            case 3:
                setScore(4, handhold[0][1], Integer.MIN_VALUE);
                break;
            case 6:
                setScore(9, handhold[0][1], Integer.MIN_VALUE);
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

    public int getScore() {
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

    public void setScore(int score) {
        this.score = score;
    }

    /**
     *
     * @param hand
     * @param firstvalue
     * @param secondvalue
     */
    public void setScore(int hand, int firstvalue, int secondvalue) {
        String[] score_ = new String[3];
        int scoret = 0;
        switch (hand) {
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
        switch (score_[0]) {
            case "High Card":
                if (score_[1] == "0") {
                    scoret = 12;
                } else {
                    scoret = Integer.parseInt(score_[1] + 1);
                }
                break;
            case "Pair":
                if (score_[1] == "0") {
                    scoret = 24;
                } else {
                    scoret = Integer.parseInt(score_[1] + 13);
                }
                break;

            case "Two Pairs":
                if (score_[1] == "0") {
                    scoret = 36;
                } else {
                    scoret = Integer.parseInt(score_[1] + 25);
                }
                break;
            case "Three of a Kind":
                if (score_[1] == "0") {
                    scoret = 48;
                } else {
                    scoret = Integer.parseInt(score_[1] + 37);
                }
                break;
            case "Straight":
                if (score_[1] == "0") {
                    scoret = 60;
                } else {
                    scoret = Integer.parseInt(score_[1] + 49);
                }
                break;
            case "Flush":
                if (score_[1] == "0") {
                    scoret = 72;
                } else {
                    scoret = Integer.parseInt(score_[1] + 61);
                }
                break;
            case "Full":
                if (score_[1] == "0") {
                    scoret = 84;
                } else {
                    scoret = Integer.parseInt(score_[1] + 73);
                }
                break;
            case "Poker":
                if (score_[1] == "0") {
                    scoret = 96;
                } else {
                    scoret = Integer.parseInt(score_[1] + 85);
                }
                break;
            case "Straight Flush":
                if (score_[1] == "0") {
                    scoret = 108;
                } else {
                    scoret = Integer.parseInt(score_[1] + 97);
                }
                break;
            case "Royal Straight":
                if (score_[1] == "0") {
                    scoret = 120;
                } else {
                    scoret = Integer.parseInt(score_[1] + 109);
                }
                break;
        }
        this.score = scoret;
    }

    public boolean getIsDealer() {
        return isDealer;
    }

}
