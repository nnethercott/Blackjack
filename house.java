package blackjack;

import java.util.ArrayList;

public class house extends person {
    ArrayList<String> visible;

    public house() {
        super();
        visible = new ArrayList<String>();
        this.generateVisible();
    }

    // decision making logic
    public boolean turnLogic() {
        // make 16 high, ignore splitting pairs and stuff
        if (this.points() >= 16) {
            return false;
        } else {
            return true;
        }
    }

    public void generateVisible() {
        visible.clear();
        for (int i = 0; i < this.hand.size(); i++) {
            if (i != 1) {
                visible.add(this.hand.get(i));
            }
        }
    }

}
