package de.thnuernberg.bme.memorybuddy.ui.card;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import de.thnuernberg.bme.memorybuddy.ui.deck.Deck;

@Entity(tableName = "Cards",
        foreignKeys = @ForeignKey(entity = Deck.class,
                parentColumns = "deck_id",
                childColumns = "deck_id",
                onDelete = ForeignKey.CASCADE))
public class Card {
    @ColumnInfo(name="card_id")
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name="question")
    private String front;
    @ColumnInfo(name="answer")
    private String back;
    @ColumnInfo(name="card_category")
    private String category;
    @ColumnInfo(name="deck_id")
    private int deckId;

    @ColumnInfo(name="card_reviewCount")
    private int reviewCount;
    @ColumnInfo(name="card_rating")
    private int rating;
    @ColumnInfo(name="card_recommendation")
    private int recommendation;

    @Ignore
    private Deck deck; // Use this field to store Deck object (not stored in database)

    public Card(String category, String front, String back,int deckId, int rating, int recommendation, int reviewCount) {
        this.front = front;
        this.back = back;
        this.category = category;
        this.deckId = deckId;
        this.reviewCount = reviewCount;
        this.rating = rating;
        this.recommendation = recommendation;
    }

    public String getFront() {
        return front;
    }
    public void setFront(String front){this.front = front;}
    public String getBack() {
        return back;
    }
    public void setBack(String back){this.back = back;}
    public String getCategory() {return category;}
    public void setCategory(String category){this.category = category;}
    public int getDeckId() {
        return deckId;
    }
    public void setDeckId(int deckId){this.deckId = deckId;}
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
    public int getRecommendation() {
        return recommendation;
    }
    public void setRecommendation(int recommendation) {
        this.recommendation = recommendation;
    }

    public int getID() {
        return id;
    }
    public void setID(int id) {this.id = id;}
    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
        this.deckId = deck.getDeckId(); // Update deckId when setting Deck object
    }

}
