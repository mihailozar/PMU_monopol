package com.example.pmu_monopol.MainMenu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NumberPlayersViewModel extends ViewModel {
    private MutableLiveData<Integer> number=new MutableLiveData<Integer>(0);

    public LiveData<Integer> getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number.setValue(number);
    }
}
