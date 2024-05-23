package de.thnuernberg.bme.memorybuddy.ui.deck;

import java.util.ArrayList;
import java.util.List;

import de.thnuernberg.bme.memorybuddy.ui.card.Card;

public class Deck {
    private final String name;
    private final List<Card> cards;
    private final int cardCount;
    private final int reviewCount;
    private final int rating;

    public Deck(String name, List<Card> cards, int cardCount) {
        this.name = name;
        this.cards = cards;
        this.cardCount = cardCount;
        this.reviewCount = 0;
        this.rating = 0;
    }


    public String getName() {
        return name;
    }
    public int getCardCount() {

        return cardCount;
    }
    public List<Card> getCards() {
        return cards;
    }

}

