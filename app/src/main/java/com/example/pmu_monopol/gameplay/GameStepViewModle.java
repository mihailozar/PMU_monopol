package com.example.pmu_monopol.gameplay;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.pmu_monopol.data.DataRepository;
import com.example.pmu_monopol.data.GameStep;
import com.example.pmu_monopol.data.GameStepRepository;
import com.example.pmu_monopol.data.MyData;

import java.util.ArrayList;
import java.util.List;

public class GameStepViewModle extends ViewModel {

    private final GameStepRepository gameStepRepository;

    private static final String GET_DATA_KEY = "get-key";
    private int typeData=0;


    @ViewModelInject
    public GameStepViewModle (
            GameStepRepository dataRepository
    ){
        this.gameStepRepository=dataRepository;

    }

    public void instertData(GameStep data) {
         gameStepRepository.insert(data);
    }
    public LiveData<List<GameStep>> getDataList() {

        return gameStepRepository.getAllLiveData();
    }

    public List<GameStep> getAll(){return  gameStepRepository.getAll();}

    public List<GameStep> getStepsByIdGame(int idGame){return gameStepRepository.getStepsByIdGame(idGame);}
}
