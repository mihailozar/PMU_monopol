package com.example.pmu_monopol.gameplay;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShakeViewModel extends ViewModel {

    private MutableLiveData<String> shakeDice=new MutableLiveData<String>("");

    public LiveData<String> getShaked() {
        return shakeDice;
    }

    public void setShaked(String value) {
        this.shakeDice.setValue(value);
    }
}
