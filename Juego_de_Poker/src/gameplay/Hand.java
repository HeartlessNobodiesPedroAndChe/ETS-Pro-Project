package gameplay;

import java.util.ArrayList;
import java.util.Scanner;



/**
 * 
 * @author Becerra Gutiérrez, Jesús Daniel
 * @author Suárez Delgado, Yared
 * @author Núñez Delgado, Eleazar
 * @author Borges Santamaría, Pedro
 */
public class Hand extends Deck {
    // Initializating Players
    Player p1, p2, p3, p4;

    /* - - - Every method showPlayerCards() is just for testing - - - */
    /* - - - As well as Unused cards print - - - */

    /**
    * This method will show the hands of four players and
    * then call another method to change cards. THIS IS A TEST APPROACH.
    */
    public void handDealt () {
    p1 = new Player("Jesús", 0, deal_cards());
    changeHand(p1);
    p2 = new Player("Yared", 5.2, deal_cards());
    changeHand(p2);
    p3 = new Player("Eleazar", 10, deal_cards());
    changeHand(p3);
    p4 = new Player("Pedro", 1.03, deal_cards());
    changeHand(p4);

    //System.out.println("Unused cards:\n" + getDeck());
    }
    
    
    /**
     * This method lets you replace the cards in the Player Object if chosen to.
     * @param p Player Object to manipulate.
     * @return p Player Object with changed values.
     */
    public Player changeHand (Player p) {
        ArrayList <Integer> change = new ArrayList<>();
        boolean ok = false;
        int value;
        String answer = "";
        Scanner scan = new Scanner(System.in);
        System.out.println("These are your cards, " + p.getName());
        System.out.println(p.showPlayerCards());
        System.out.print("Do you want to replace cards? (y/n): ");
        //This loop ensures that a valid input is entered.
        do {
            answer = scan.nextLine();
            if (answer.equals("y") || answer.equals("n")) {
                ok = true;
            } else {
                System.out.print("You have to introduce 'y' or 'n': ");
            }
            
        } while (ok == false);
        
        
        if (answer.equals("y")) {
            System.out.print("Which ones do you want to replace? [Enter '0' when you are done]: ");
            //This loop will gather the values of the cards to replace.
            do {
                value = scan.nextInt();
                if (value != 0 && value <= 5) {
                    change.add(value - 1);
                }

                if (change.size() == 5) {
                    break;
                }

            } while (value != 0);
        //Cards will be replaced by calling a few methods and then it will return the edited Object.
            p.setHandhold_cards(change_cards(p.getHandhold_cards(), change));
            System.out.println("\nThis is your new hand, " + p.getName());
            System.out.println(p.showPlayerCards());
        }
        System.out.println("\nGood Luck!\n");
        return p;
    }
}
