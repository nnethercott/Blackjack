package blackjack;

import java.util.ArrayList;

public class player extends person {
    public int funds;

    public player() {
        super();
        this.funds = 1000;
    }

    public player(int funds) {
        super();
        this.funds = funds;
    }

}
