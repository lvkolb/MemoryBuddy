package de.thnuernberg.bme.memorybuddy.ui.statistic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StatisticViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public StatisticViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is setatistic fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
