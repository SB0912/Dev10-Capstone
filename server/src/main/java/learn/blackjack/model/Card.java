package learn.blackjack.model;

public class Card {
    int cardId;
    CardEnum cardEnum;
    CardSuit cardSuit;

    boolean inDeck;

    int value;
    String fileDisplay;

    String url;

    public int getValue() {
        return cardEnum.getValue();
    }

    public void setValue(int lowValue) {
        this.value = lowValue;
    }

    public String getFileDisplay() {
        return fileDisplay;
    }

    public void setFileDisplay(String fileDisplay) {
        this.fileDisplay = fileDisplay;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public CardEnum getCardEnum() {
        return cardEnum;
    }

    public void setCardEnum(CardEnum cardEnum) {
        this.cardEnum = cardEnum;
    }

    public CardSuit getCardSuit() {
        return cardSuit;
    }

    public void setCardSuit(CardSuit cardSuit) {
        this.cardSuit = cardSuit;
    }

    public boolean isInDeck() {
        return inDeck;
    }

    public void setInDeck(boolean inDeck) {
        this.inDeck = inDeck;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
