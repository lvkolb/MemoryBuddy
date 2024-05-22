package de.thnuernberg.bme.memorybuddy.ui.card;

public class Card {
    private String front;
    private String back;
    private String name;
    private String deck;
    private String tag;

    private int reviewCount;
    private int rating;

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
