package de.thnuernberg.bme.memorybuddy.ui.card;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "Card")
public class Card {
    @ColumnInfo(name="card_id")
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name="question")
    private String front;
    @ColumnInfo(name="answer")
    private String back;
    @ColumnInfo(name="card_name")
    private String name;
    @ColumnInfo(name="card_deck")
    private String deck;
    @ColumnInfo(name="card_tag")
    private String tag;

    @ColumnInfo(name="card_reviewCount")
    private int reviewCount;
    @ColumnInfo(name="card_rating")
    private int rating;

    public Card(String name, String front, String back,String deck, String tag) {
        this.front = front;
        this.back = back;
        this.name = name;
        this.deck = deck;
        this.tag = tag;
        this.reviewCount = 0;
        this.rating = 0;
        this.id = 0;
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
    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }
    public int getReviewCount() {
        return reviewCount;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
}
