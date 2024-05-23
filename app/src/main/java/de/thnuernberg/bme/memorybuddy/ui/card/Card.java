package de.thnuernberg.bme.memorybuddy.ui.card;

public class Card {
    private final String front;
    private final String back;
    private final String name;
    private final String deck;
    private final String tag;

    private final int reviewCount;
    private final int rating;

    public Card(String front, String back, String name, String deck, String tag) {
        this.front = front;
        this.back = back;
        this.name = name;
        this.deck = deck;
        this.tag = tag;
        this.reviewCount = 0;
        this.rating = 0;
    }

    public String getFront() {
        return front;
    }

    public String getBack() {
        return back;
    }
    public String getName() {
        return name;
    }
    public String getDeck() {
        return deck;
    }
    public String getTag() {
        return tag;
    }
}
