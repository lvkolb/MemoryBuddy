package de.thnuernberg.bme.memorybuddy.ui.deck;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

import de.thnuernberg.bme.memorybuddy.ui.card.Card;

@Entity(tableName = "Decks")
public class Deck {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    @Ignore
    private List<Card> cards; // Ignore because it's not stored in database

    private int cardCount;

    private int reviewCount;

    private int rating;

    public Deck(String name, int cardCount, int reviewCount, int rating) {
        this.name = name;
        this.cardCount = cardCount;
        this.reviewCount = reviewCount;
        this.rating = rating;
    }

    // Getters and setters
    public int getId() {
        return id;
    }
    public int getDeckId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public int getCardCount() {
        return cardCount;
    }

    public void setCardCount(int cardCount) {
        this.cardCount = cardCount;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
