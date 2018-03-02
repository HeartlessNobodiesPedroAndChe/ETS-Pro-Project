/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import gameplay.*; 

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
        Player p1 = new Player(), p2 = new Player(), p3 = new Player(), p4 = new Player();
        
        // Initializating Game with Players and Deck
        Game game = new Game(deck, p1, p2, p3, p4);
        
        game.game_start();
    }

}
