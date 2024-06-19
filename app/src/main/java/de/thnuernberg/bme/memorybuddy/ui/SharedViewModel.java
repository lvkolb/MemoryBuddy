package de.thnuernberg.bme.memorybuddy.ui;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import de.thnuernberg.bme.memorybuddy.database.CardDatabase;
import de.thnuernberg.bme.memorybuddy.ui.card.Card;

public class SharedViewModel extends ViewModel {

    private LiveData<List<Card>> cardsLiveData;
    private LiveData<List<Card>> recommendedCards;
    private CardDatabase cardDatabase;

    public void setCardDatabase(CardDatabase cardDatabase) {
        this.cardDatabase = cardDatabase;
    }

    public LiveData<List<Card>> getCards() {
        if (cardsLiveData == null) {
            loadAllCards();
        }
        return cardsLiveData;
    }

    private void loadAllCards() {
        cardsLiveData = cardDatabase.getCardDAO().getAllCard();
    }
    public LiveData<List<Card>> getRecommendedCards() {
        recommendedCards = cardDatabase.getCardDAO().getCardRecommended();
        return recommendedCards;
    }

    public CardDatabase getCardDatabase() {
        return cardDatabase;
    }
}