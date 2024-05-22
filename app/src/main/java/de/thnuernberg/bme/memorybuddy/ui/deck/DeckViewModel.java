package de.thnuernberg.bme.memorybuddy.ui.deck;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DeckViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DeckViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is deck fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}