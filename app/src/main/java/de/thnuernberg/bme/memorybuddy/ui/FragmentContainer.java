package de.thnuernberg.bme.memorybuddy.ui;

import de.thnuernberg.bme.memorybuddy.ui.card.Card;

public interface FragmentContainer {
    void onCardUpdated(int id,String category, String frontText, String backText, int deckId, int rating, int recommendation , int reviewCount);
    void onCardPlayed(int id,String category, String frontText, String backText, int deckId, int rating, int recommendation , int reviewCount);
}
