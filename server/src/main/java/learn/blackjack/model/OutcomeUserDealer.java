package learn.blackjack.model;

import java.util.ArrayList;
import java.util.List;

public class OutcomeUserDealer {

    List<CardListUser> cardListUsers = new ArrayList<>();
    CardListDealer cardListDealer;

//    dont use messages for anything
    List<String> messages = new ArrayList<>();

    public List<String> getMessages() {
        return messages;
    }

    public void addMessage (String message) {
        messages.add(message);
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public List<CardListUser> getCardListUsers() {
        return cardListUsers;
    }

    public void setCardListUsers(List<CardListUser> cardListUsers) {
        this.cardListUsers = cardListUsers;
    }

    public void addCardListUser (CardListUser cardListUser) {
        this.cardListUsers.add(cardListUser);
    }

    public CardListDealer getCardListDealer() {
        return cardListDealer;
    }

    public void setCardListDealer(CardListDealer cardListDealer) {
        this.cardListDealer = cardListDealer;
    }
}
