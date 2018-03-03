package gameplay;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

/**
 * @version 0.1 Early
 * @author Becerra Gutiérrez, Jesús Daniel
 * @author Suárez Delgado, Yared
 * @author Núñez Delgado, Eleazar
 * @author Borges Santamaría, Pedro
 * @see Player
 * @see Deck
 */
public class Game {

    // Variables
    private boolean inGame = false;
    private double bet = 0;
    private double max_bet = 0;
    private final double small_blind = 1;
    private final double big_blind = small_blind * 2;
    private int playingPlayerIndex;
    private int smallBlindIndex;
    private int bigBlindIndex;
    private Player[] players;
    private Deck deck;

    /**
     * This constructor basically starts a game.<br>
     * <ul>
     * <li>Initializes <code>Deck Object</code></li>
     * <li>Asks for every Player's name</li>
     * <li>Give 5 cards to every player</li>
     * <li>Sets a random Dealer</li>
     * </ul>
     *
     * @param players Array of Players (need to be initializated)
     */
    public Game(Player... players) {
        Scanner input = new Scanner(System.in);
        deck = new Deck();
        this.players = players;

        for (int i = 0; i < players.length; i++) {
            System.out.print("Player Nº " + (i + 1) + ", write your name: ");
            players[i].setName(input.nextLine());
            players[i].setHandhold_cards(deck.deal_cards());
        }

        setInGame();
        playingPlayerIndex = set_dealer();
    }

    /**
     * This constructor gets a <b>Virtual Deck</b> and starts the game.<br>
     * <ul>
     * <li>Asks for every Player's name</li>
     * <li>Give 5 cards to every player</li>
     * <li>Sets a random Dealer</li>
     * </ul>
     *
     * @param deck The <b>Virtual Deck</b> to play with.
     * @param players Array of Players (need to be initializated) (They can be
     * just Players separeted by commas)
     */
    public Game(Deck deck, Player... players) {
        Scanner input = new Scanner(System.in);
        this.players = players;
        this.deck = deck;

        for (int i = 0; i < players.length; i++) {
            System.out.print("Player Nº " + (i + 1) + ", write your name: ");
            players[i].setName(input.nextLine());
            players[i].setHandhold_cards(deck.deal_cards());
        }

        setInGame();
        playingPlayerIndex = set_dealer();
    }

    /**
     * This method starts a game and manage everything realted with it.
     */
    public void game_start() {

        while (inGame) {
            
            // We set who's gonna make small and big blind
            setSmallBlind();
            setBigBlind();

            // The gameplay is based on two rounds
            for (int i = 0; i < 2; i++) {
                
                for (Player player : players) {
                    // Player
                    System.out.println("\nPlayer '" + players[playingPlayerIndex].getName() + "', it's your turn:");
                    showMoney();
                    changeHand(players[playingPlayerIndex]);
                    players[playingPlayerIndex].matchHands();
                    playerBet(player);
                    // Change to next Player
                    next_player();
                }
                System.out.println("The winner of the round is: " + players[RoundWinner()].getName());
            }
            setInGame();
        }

    }
    
    /**
     * This method check if playingPlayerIndex is higher than players length.<br>
     * If it is, it restarts to the very first player so we make sure every player plays.
     */
    private void next_player() {
        if (playingPlayerIndex + 1 > players.length - 1) {
            playingPlayerIndex = 0;
        } else {
            ++playingPlayerIndex;
        }
        // A really big line break
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
    
    /**
     * This method lets you replace the cards in the Player Object if chosen to.
     * @param p Player Object to manipulate.
     * @return p Player Object with changed values.
     * @see Player
     * @see ArrayList
     */
    public Player changeHand(Player p) {
        ArrayList<Integer> change = new ArrayList<>();
        String answer;
        Scanner scan = new Scanner(System.in);
        boolean everything_is_aright = true;
        System.out.println("These are your cards:");
        System.out.println(p.showPlayerCards());
        System.out.print("Do you want to replace cards? (y/n): ");
        //This loop ensures that a valid input is entered.
        do {
            // We read the answer and parse it to lowerCase
            answer = scan.nextLine();
            answer = answer.toLowerCase();
            
            // We check if the answer pass our pattern
            if (!answer.matches("(y|n)")) {
                System.out.print("You have to introduce 'y' or 'n': ");
            } else {
                
                if (answer.equals("y")) {
                    // If the Player wants to change cards
                    System.out.print("Which ones do you want to replace? ");
                    
                    // Split will get a whole String and separate it into an array by character in *.split(char)
                    String[] cardsIndexes = scan.nextLine().split(" ");
                    
                    // Checking Numbers
                    do {
                        
                        // We declare a temp Integer for parsingChecker
                        int cardIndex_;
                        
                        // Get every cardIndex
                        for(String cardIndex: cardsIndexes) {
                            
                            // Try to parse and get errors
                            try {
                                cardIndex_ = Integer.parseInt(cardIndex);
                                
                                // Check values introduced
                                if (cardIndex_ <= 0 || cardIndex_ > 5) {
                                    System.out.println("You can't set values below 1 or above 5.");
                                    everything_is_aright = false;
                                } else {
                                    everything_is_aright = true;
                                }
                                
                            // Catching errors
                            } catch(NumberFormatException e) {
                                System.out.println("You can't write letters");
                                everything_is_aright = false;
                            }
                        }
                        
                        // Repeating input
                        if (!everything_is_aright) {
                            System.out.print("Which ones do you want to replace? ");
                            cardsIndexes = scan.nextLine().split(" ");
                        }
                    
                    } while (!everything_is_aright);


                    // For-Loop will get every String in an array String
                    for (String cardIndex : cardsIndexes) {
                        // We parse that value to Integer and set it into change ArrayList
                        change.add(Integer.parseInt(cardIndex) - 1);
                    }
                    
                    // We change the hand and show it
                     p.setHandhold_cards(deck.change_cards(p.getHandhold_cards(), change));
                    System.out.println("\nThis is your new hand, " + p.getName());
                    System.out.println(p.showPlayerCards());
                    
                    // Dummy function so people now can read their cards
                    System.out.print("Press Enter key to continue...");
                    try{System.in.read();}catch(Exception e){};
                }
                
            }
        } while (!answer.toLowerCase().matches("(y|n)"));
        
        return p;
    }
    
    /**
     * This method sets a random dealer for the game to start and returns its
     * index.<br>
     * Sets <code>isDealer = true;</code> and <code>isPlaying = true;</code>
     *
     * @return index as <code>Integer</code> setting player[index] as dealer.
     * @see Random
     */
    private int set_dealer() {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(players.length - 1);
        players[index].setDealer();
        players[index].setPlaying();
        System.out.println("\nThe dealer is " + players[index].getName());
        return index;
    }
    
    /**
     * Asks for small blind to the player next to the Dealer.
     */
    private void setSmallBlind() {
        // We check if players[playingPlayerIndex + 1] exists and give it the attribute boolean SmallBlind
        smallBlindIndex = playingPlayerIndex + 1;
        if (smallBlindIndex > players.length - 1) {
            smallBlindIndex = 0;
            players[smallBlindIndex].set_SmallBlind();
            System.out.println("Player " + players[smallBlindIndex].getName() + " you must set the Small Blind:");
            players[smallBlindIndex].setPlaying();
        } else {
            players[smallBlindIndex].set_SmallBlind();
            System.out.println("Player " + players[smallBlindIndex].getName() + " you must set the Small Blind:");
            players[smallBlindIndex].setPlaying();
        }
    }
    
    /**
     * Asks for small blind to the player next to the Small Blind Player.
     */
    private void setBigBlind() {
        // We check if players[playingPlayerIndex + 2] exists and give it the attribute boolean BigBlind
        bigBlindIndex = smallBlindIndex + 1;
        if (bigBlindIndex > players.length - 1) {
            bigBlindIndex = 0;
            players[bigBlindIndex].set_BigBlind();
            System.out.println("Player " + players[bigBlindIndex].getName() + " you must set the Big Blind:");
            players[bigBlindIndex].setPlaying();
        } else {
            players[bigBlindIndex].set_SmallBlind();
            System.out.println("Player " + players[bigBlindIndex].getName() + " you must set the Big Blind:");
            players[bigBlindIndex].setPlaying();
        }
    }
    
    /**
     *
     * @return
     */
    public int RoundWinner() {
        int max = 0, WinnerIndex = 0;
        for (int i = 0; i < players.length; i++) {
            
        }
        return WinnerIndex;
    }

    /**
     * Method that show player's money
     */
    private void showMoney() {
        System.out.println(players[playingPlayerIndex].getName() + " your money balance is: " + players[playingPlayerIndex].getMoney() + "\n");
    }
    
    /**
     * This method asks the players how much they want to bet and add the bets 
     * in the variable <code>Max_bet <code>
     */
    private void playerBet(Player player) {
        Scanner input = new Scanner(System.in);
        boolean ok = true;
        int bet;
        // Dummy variable
        String _bet;
        //This If equals players to BigBlind and SmallBlind and shows their bets
        if (player == players[bigBlindIndex]) {
            double leeSin = big_blind;
            System.out.println("'" + players[playingPlayerIndex].getName() + "' your currently bet is: " + leeSin + " $");
            players[playingPlayerIndex].setMoney((players[playingPlayerIndex].getMoney() - leeSin));
            System.out.println("Your currently money is: " + players[playingPlayerIndex].getMoney());
        
        } else if (player == players[smallBlindIndex]) {
            double leeSin = small_blind;
            System.out.println("'" + players[playingPlayerIndex].getName() + "' your currently bet is: " + leeSin + " $");
            players[playingPlayerIndex].setMoney((players[playingPlayerIndex].getMoney() - leeSin));
            System.out.println("Your currently money is: " + players[playingPlayerIndex].getMoney());
        }
        
        //Me falta hacer que despues de cada supuesto salte directamente a tu parte de código en el que pide
        //la cantidad que desea apostar y que le diga cual es la apuesta más alta de la mesa ahora mismo
        //para decidir si apuesta o no
        
        System.out.print("\n" + player.getName() + " how much money do you want to bet? ");
        _bet = input.next();
            
        do {
            
            // We check if the input is a number
            try {
                bet = Integer.parseInt(_bet);
                
                // We check if the player can afford the bet
                if (player.getMoney() >= bet) {
                    player.setMoney(player.getMoney() - bet);
                    setMax_bet(getMax_bet() + bet);
                    ok = true;
                } else {
                    System.out.println("You don't have enough money to do that.");
                    ok = false;
                }
        
            } catch(NumberFormatException e) {
                System.out.println("The bet must be a number.");
                ok = false;
    }
    
            if (!ok) {
                System.out.print("\n" + player.getName() + " how much money do you want to bet? ");
                _bet = input.next();
            }

        } while (!ok);

    }

    /**
     * This method is used to distribute the winnings of the
     * round to each player.
     */
    private void dist_Money() {
       double timba = getMax_bet();
       //When the round ends the winner will receive the winnings
    }

    /**
     * Simple method to change inGame to its negative.<br>
     * For example:<br> {@code if inGame == true; inGame = false;}<br>
     * {@code if inGame == false; inGame = true;}
     */
    public void setInGame() {
        inGame = !inGame;
    }

    /**
     * Simple method to manually change boolean <code>inGame</code>.
     * @param inGame The boolean to change Game's inGame
     */
    public void setManualInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public void setBet(double bet) {
        this.bet = bet;
    }

    public void setMax_bet(double max_bet) {
        this.max_bet = max_bet;
    }

    public boolean isInGame() {
        return inGame;
    }

    public double getBet() {
        return bet;
    }

    public double getMax_bet() {
        return max_bet;
    }

    public double getSmall_blind() {
        return small_blind;
    }

    public double getBig_blind() {
        return big_blind;
    }

}
