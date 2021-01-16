package blackjack;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;

public class deck {
    private ArrayList<String> cards;

    // deck template
    private static final String[] deck_template = { "2H", "3H", "4H", "5H", "6H", "7H", "8H", "9H", "10H", "JH", "QH",
            "KH", "AH", "2C", "3C", "4C", "5C", "6C", "7C", "8C", "9C", "10C", "JC", "QC", "KC", "AC", "2S", "3S", "4S",
            "5S", "6S", "7S", "8S", "9S", "10S", "JS", "QS", "KS", "AS", "2D", "3D", "4D", "5D", "6D", "7D", "8D", "9D",
            "10D", "JD", "QD", "KD", "AD" };

    // Implementing Fisher–Yates shuffle (Stack...)
    static void shuffleArray(ArrayList<String> ar) {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.size() - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            String a = ar.get(index);
            ar.set(index, ar.get(i));
            ar.set(i, a);
        }
    }

    public static ArrayList<String> buildDeck(int i) {
        // copy contents to a temp deck
        ArrayList<String> cards = new ArrayList<String>();

        for (int j = 0; j < i; j++) {
            for (String item : deck_template) {
                cards.add(item);
            }
        }
        // shuffle
        shuffleArray(cards);
        return cards;
    }

    public String deal() {
        int len = this.cards.size();
        String card = this.cards.get(len - 1);
        this.cards.remove(len - 1);
        return card;
    }

    public int decklength() {
        return this.cards.size();
    }

    public deck() {
        this.cards = deck.buildDeck(1);
    }

    public deck(int numdecks) {
        if (numdecks >= 1) {
            this.cards = deck.buildDeck(numdecks);
        } else {
            this.cards = deck.buildDeck(1);
        }

    }

}
