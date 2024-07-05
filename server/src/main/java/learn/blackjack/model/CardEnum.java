package learn.blackjack.model;

public enum CardEnum {
    ACE(11, "ace"),
    KING(10, "king"),
    QUEEN(10,"queen"),
    JACK(10, "jack"),
    TEN(10, "10"),
    NINE(9, "9"),
    EIGHT(8, "8"),
    SEVEN(7, "7"),
    SIX(6, "6"),
    FIVE(5, "5"),
    FOUR(4, "4"),
    THREE(3, "3"),
    TWO(2,"2");

    private int value;
    private final String fileDisplay;

    CardEnum(int value, String fileDisplay) {
        this.value = value;

        this.fileDisplay = fileDisplay;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getFileDisplay() {
        return fileDisplay;
    }
}
