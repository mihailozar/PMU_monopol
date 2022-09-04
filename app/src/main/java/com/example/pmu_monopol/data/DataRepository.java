package com.example.pmu_monopol.data;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

public class DataRepository {



    private final MyDataDao dataDao;

    @Inject
    public DataRepository(
            MyDataDao dataDao) {

        this.dataDao = dataDao;
    }

    public long insert(MyData data) {
        return dataDao.insert(data);
    }

    public List<MyData> getAll() {
        return dataDao.getAll();
    }

    public LiveData<List<MyData>> getAllLiveData() {
        return dataDao.getAllLiveData();
    }

    public MyData getById( long id){return dataDao.getGameById(id);}
    public void updateGameWinner(long id , String winner){ dataDao.updateGameWinner(id,winner);}
    

}
