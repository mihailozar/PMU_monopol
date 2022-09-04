package com.example.pmu_monopol.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface GameStepDao {

    @Insert
    long insert(GameStep gameStep);

    @Query("SELECT * FROM GameStep")
    List<GameStep> getAll();

    @Query("SELECT * FROM GameStep")
    LiveData<List<GameStep>> getAllLiveData();

    @Query("SELECT * FROM GameStep WHERE idGame=:idGame")
    List<GameStep> getStepsByIdGame(int idGame);

//    @Query("SELECT * FROM MyData WHERE idGame=:id")
//    MyData getGameById(long id);
}
