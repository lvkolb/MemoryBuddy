package de.thnuernberg.bme.memorybuddy.ui.deck;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import de.thnuernberg.bme.memorybuddy.ui.card.Card;

public class DeckViewModel extends ViewModel {

    private MutableLiveData<List<Deck>> mDecks;

    public DeckViewModel() {
        mDecks = new MutableLiveData<>();
        mDecks.setValue(new ArrayList<>());
    }

    public LiveData<List<Deck>> getDecks() {
        return mDecks;
    }

    public void addDeck(Deck deck) {
        List<Deck> currentDecks = mDecks.getValue();
        if (currentDecks != null) {
            currentDecks.add(deck);
            mDecks.setValue(currentDecks);
        }
    }
}