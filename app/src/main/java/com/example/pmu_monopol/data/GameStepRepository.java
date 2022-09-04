package com.example.pmu_monopol.data;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

public class GameStepRepository {

    private final GameStepDao gameStepDao;
    private  ExecutorService executorService;

    @Inject
    public GameStepRepository(
            ExecutorService executorService,
            GameStepDao gameDao) {
        this.executorService=executorService;
        this.gameStepDao = gameDao;
    }

    public void insert(GameStep data) {
        executorService.submit(()->{gameStepDao.insert(data);});

    }

    public List<GameStep> getAll() {
        return gameStepDao.getAll();
    }

    public LiveData<List<GameStep>> getAllLiveData() {
        return gameStepDao.getAllLiveData();
    }
    public List<GameStep> getStepsByIdGame(int idGame){return gameStepDao.getStepsByIdGame(idGame);}
//    public MyData getById( long id){return gameStepDao.getGameById(id);}
}
