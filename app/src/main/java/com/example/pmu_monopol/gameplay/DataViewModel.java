package com.example.pmu_monopol.gameplay;

import androidx.hilt.lifecycle.ViewModelInject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.pmu_monopol.data.DataRepository;
import com.example.pmu_monopol.data.MyData;
import com.example.pmu_monopol.data.MyDataDao;

import java.util.List;

public class DataViewModel extends ViewModel {

    private final DataRepository dataRepository;

    private static final String GET_DATA_KEY = "get-key";
    private int typeData=0;


    @ViewModelInject
    public DataViewModel (
            DataRepository dataRepository
    ){
        this.dataRepository=dataRepository;

    }

    public long instertData(MyData data) {
        return dataRepository.insert(data);
    }
    public LiveData<List<MyData>> getDataList() {

        return dataRepository.getAllLiveData();
    }

    public List<MyData> getAll(){return  dataRepository.getAll();}
    public MyData getGameById(long id) {
        return dataRepository.getById(id);
    }
    public void updateGameById(long id,String winner){dataRepository.updateGameWinner(id,winner);}
}
