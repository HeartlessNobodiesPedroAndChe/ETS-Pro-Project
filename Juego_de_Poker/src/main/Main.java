/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import gameplay.Deck;
import gameplay.Player;

/**
 *
 * @author Becerra Gutiérrez, Jesús Daniel
 * @author Suárez Delgado, Yared
 * @author Núñez Delgado, Eleazar
 * @author Borges Santamaría, Pedro
 */
// This class will be the launcher
public class Main {

    public static void main(String[] args) {
        // Initializating deck object
        Deck deck = new Deck();
        
        // Initializating Players
        Player p1, p2, p3, p4;
        
        /* - - - Every method showPlayerCards() is just for testing - - - */
        /* - - - As well as Unused cards print - - - */
        
        p1 = new Player("Jesús", 0, deck.deal_cards());
        System.out.println(p1.showPlayerCards());
        
        p2 = new Player("Yared", 5.2, deck.deal_cards());
        System.out.println(p2.showPlayerCards());
        
        p3 = new Player("Eleazar", 10, deck.deal_cards());
        System.out.println(p3.showPlayerCards());
        
        p4 = new Player("Pedro", 1.03, deck.deal_cards());
        System.out.println(p4.showPlayerCards());
        
        System.out.println("Unused cards:\n" + deck.getDeck());
    }

}
