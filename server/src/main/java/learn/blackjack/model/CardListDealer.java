package learn.blackjack.model;

import java.util.ArrayList;
import java.util.List;

public class CardListDealer {
    List<Card> cardList = new ArrayList<>();

    boolean didBust;

    int whichRound;

    int total;

    public boolean isDidBust() {
        return didBust;
    }

    public void setDidBust(boolean didBust) {
        this.didBust = didBust;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
    }

    public int getWhichRound() {
        return whichRound;
    }

    public void setWhichRound(int whichRound) {
        this.whichRound = whichRound;
    }
}
