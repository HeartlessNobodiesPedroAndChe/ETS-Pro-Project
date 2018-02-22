package deck;

import java.util.Arrays;

/**
 *
 * @version 0.1 Early
 * @author Becerra Gutiérrez, Jesús Daniel
 * @author Suárez Delgado, Yared
 * @author Núñez Delgado, Eleazar
 * @author Borges Santamaría, Pedro
 */
// Antes que nada recordad añadir vuestros nombres a @author
public class Deck {

    int[][] deck = new int[4][13];

    public Deck() {
        this.deck = load_deck();
        System.out.println(watch_deck(deck));
    }

    /**
     * This method sets values to the attribute deck once Deck Object is
     * initializated.
     *
     * @return deck_loaded as <code>Integer</code>
     */
    private static int[][] load_deck() {
        int[][] deck_loaded = new int[4][13];
        for (int i = 0; i < deck_loaded.length; i++) {
            int valor = 1;
            for (int j = 0; j < deck_loaded[i].length; j++) {
                deck_loaded[i][j] = valor;
                ++valor;
            }
        }
        return deck_loaded;
    }

    private String watch_deck(int[][] deck) {
        return Arrays.deepToString(deck);
    }

}
