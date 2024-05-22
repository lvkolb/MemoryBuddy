package de.thnuernberg.bme.memorybuddy.ui.card;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class CardViewModel extends ViewModel {

    private MutableLiveData<List<Card>> mCards;

    public CardViewModel() {
        mCards = new MutableLiveData<>();
        mCards.setValue(new ArrayList<>());
    }

    public LiveData<List<Card>> getCards() {
        return mCards;
    }

    public void addCard(Card card) {
        List<Card> currentCards = mCards.getValue();
        if (currentCards != null) {
            currentCards.add(card);
            mCards.setValue(currentCards);
        }
    }
}