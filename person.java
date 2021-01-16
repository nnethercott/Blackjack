package blackjack;

import java.util.ArrayList;

public class person {
    public ArrayList<String> hand;
    int handValue;

    public person() {
        this.hand = new ArrayList<String>();
        this.handValue = 0;
    }

    public int points() {
        // gonna need logic to convert ace to 1 -- don't like what i currrently got but
        // hey
        boolean ace = false;
        int sum = 0;
        for (String st : this.hand) {
            char c = st.charAt(0);
            if (Character.isDigit(c) && st.length() == 2) {
                sum += Character.getNumericValue(c);
            } else if (Character.isDigit(c) && st.length() == 3) {
                sum += 10;
            } else {
                if (c == 'A') {
                    ace = true;
                } else {
                    sum += 10;
                }
            }
        }
        if (ace) {
            if ((sum + 11) > 21) {
                sum += 1;
            } else {
                sum += 11;
            }
        }
        this.handValue = sum;
        return sum;

    }

    public void discard() {
        this.hand.clear();
        this.handValue = 0;
    }

}
