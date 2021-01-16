package blackjack;

import java.util.*;
import javax.swing.*;

public class game {
    private deck gamedeck;
    private player whale;
    private house dealer;
    int fee;
    Scanner s;

    public game() {
        this.s = new Scanner(System.in);
        this.gamedeck = new deck(1);
        this.fee = 20;
        this.whale = new player();
        this.dealer = new house();

    }

    public game(int numdecks) {
        this.s = new Scanner(System.in);
        this.gamedeck = new deck(numdecks);
        this.fee = 20;
        this.whale = new player();
        this.dealer = new house();

    }

    public game(int numdecks, int fee) {
        this.s = new Scanner(System.in);
        this.gamedeck = new deck(numdecks);
        this.fee = fee;
        this.whale = new player();
        this.dealer = new house();
    }

    public game(int numdecks, int fee, int funds) {
        this.s = new Scanner(System.in);
        this.gamedeck = new deck(numdecks);
        this.fee = fee;
        this.whale = new player(funds);
        this.dealer = new house();
    }

    // payout scheme
    public int payout(String wintype) {
        if (wintype.equals("blackjack")) {
            return 2 * this.fee;
        } else if (wintype.equals("standard")) {
            return this.fee;
        } else if (wintype.equals("loss")) {
            return -this.fee;
        } else { // stupid
            return 0;
        }
    }

    public void turnStart() {
        System.out.println("------------------------------------------------------------");

        // player deal
        String card1 = this.gamedeck.deal();
        String card2 = this.gamedeck.deal();
        this.whale.hand.add(card1);
        this.whale.hand.add(card2);

        // house deal
        String card3 = this.gamedeck.deal();
        String card4 = this.gamedeck.deal();
        this.dealer.hand.add(card3);
        this.dealer.hand.add(card4);
        this.dealer.generateVisible();

        // okay so java gui stuff is being weird ://
        System.out.println("\nYour deal: " + this.whale.hand + " (total: " + this.whale.points() + ") "
                + "------- House: " + this.dealer.visible);
    }

    // start with turnStart, options pane and player choice...
    public void turnMiddle() {
        // first check for natural blackjack
        if (this.whale.points() == 21) {
            this.whale.funds += payout("blackjack");
            System.out.println("\nBlackjack! YOU WIN");
            return;
        }

        // while condition, ask player to hit or pass, exit when multiple conditions met
        boolean playerEnd = false;
        boolean dealerEnd = false;
        boolean playerBust = false;
        boolean dealerBust = false;

        while (!playerEnd) {
            // player goes first
            System.out.print("\nDo you wish to hit?  ");
            // throw in a while loop to only get y or n
            answer: while (true) {
                String r = this.s.nextLine();
                if (r.equals("y")) {
                    // deal and add card to hand
                    String card = this.gamedeck.deal();
                    this.whale.hand.add(card);

                    if (this.whale.points() > 21) {
                        System.out.println("\nYour hand: " + this.whale.hand + " (total: " + this.whale.points()
                                + ">21) " + " --- YOU LOSE");
                        playerEnd = true;
                        playerBust = true;
                        this.whale.funds += payout("loss");
                        return;
                    } else {
                        System.out.println("\nYour hand: " + this.whale.hand + " (total: " + this.whale.points() + ")");
                        break answer;
                    }
                } else if (r.equals("n")) {
                    playerEnd = true;
                    break answer;
                }
            }
        }
        while (!dealerEnd) {
            // house logic now
            boolean toHit = this.dealer.turnLogic();
            if (toHit) {
                String card = this.gamedeck.deal();
                this.dealer.hand.add(card);
                if (this.dealer.points() > 21) {
                    System.out.println("\nDealer Bust -- YOU WIN");
                    this.whale.funds += payout("standard");
                    dealerEnd = true;
                    dealerBust = true;
                } else {
                    // print all cards except for the second
                    this.dealer.generateVisible();
                    System.out.println("\nHouse cards: " + this.dealer.visible);
                }

            } else {
                dealerEnd = true;
            }
        }

        // now compare in the offchance
        if (!playerBust && !dealerBust) {
            System.out.println("\nYour hand: " + this.whale.hand + " (total: " + this.whale.points() + ") "
                    + "------- House: " + this.dealer.hand + " (total: " + this.dealer.points() + ") ");
            if (this.whale.handValue > this.dealer.handValue) {
                System.out.println("\nYOU WIN");
                this.whale.funds += payout("standard");
            } else {
                System.out.println("\nYOU LOSE");
                this.whale.funds += payout("loss");
            }
        }
    }

    public boolean turnEnd() {
        // discard hands
        this.whale.discard();
        this.dealer.discard();
        // print funds and ask if they want to continue
        System.out.print("\nYou now have: $" + this.whale.funds + ". Do you wish to continue?  ");
        // replace this with the java swing cause some weird issue shows up with
        // scanner...
        while (true) {
            String r = this.s.nextLine();
            if (r.equals("y")) {
                return true;
            } else if (r.equals("n")) {
                return false;
            }
        }
    }

    public void playgame() {
        boolean atTable = true;
        while (atTable && (this.gamedeck.decklength() != 0) && (this.whale.funds > 0)) {
            this.turnStart();
            this.turnMiddle();
            atTable = this.turnEnd();
        }
        if (this.gamedeck.decklength() == 0) {
            System.out.println("\nout of cards");
        }
        if (this.whale.funds <= 0) {
            System.out.println("\nyou broke");
        }
        if (!atTable) {
            System.out.println("\ncome again soon!");
        }
    }

    public static void main(String[] args) {
        game g = new game(4);
        g.playgame();
    }

}
