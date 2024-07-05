package learn.blackjack.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CardListUser {
    public CardListUser(int index, List<Card> cardList, BigDecimal bet) {
        this.index = index;
        this.cardList = cardList;
        this.bet = bet;
    }
    public CardListUser(){}

    List<Card> cardList = new ArrayList<>();
    int index;

    int whichRound;
    BigDecimal bet;
    boolean stay;
    boolean split;
    boolean doubleDown;
    boolean hit;
    boolean didBust;

    public boolean isCanBet() {
        return canBet;
    }

    public void setCanBet(boolean canBet) {
        this.canBet = canBet;
    }

    boolean canBet;
    int total;

    int didWin;
    List<String> messages = new ArrayList<>();

    public int getDidWin() {
        return didWin;
    }

    public void setDidWin(int didWin) {
        this.didWin = didWin;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<String> getMessages() {
        return messages;
    }
    public void addMessage (String message) {
        this.messages.add(message);
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public boolean isStay() {
        return stay;
    }

    public boolean isSplit() {
        return split;
    }

    public boolean isDoubleDown() {
        return doubleDown;
    }

    public boolean isHit() {
        return hit;
    }

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

    List<String> errorMessages = new ArrayList<>();
    public void addErrorMessage( String errorMessage ){
        errorMessages.add(errorMessage);
    }


    public void setSplit(boolean split) {
        this.split = split;
    }


    public void setDoubleDown(boolean doubleDown) {
        this.doubleDown = doubleDown;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public List<String> getErrorMessages() {
        //return a copy so the outside world can't mutate errors after
        //they've been added
        return new ArrayList<>(errorMessages);
    }

    public BigDecimal getBet() {
        return bet;
    }

    public void setBet(BigDecimal bet) {
        this.bet = bet;
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

    public void setStay(boolean stay) {
        this.stay = stay;
    }


}
