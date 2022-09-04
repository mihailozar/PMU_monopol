package com.example.pmu_monopol.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MyDataDao {

    @Insert
    long insert(MyData data);

    @Query("SELECT * FROM MyData")
    List<MyData> getAll();

    @Query("SELECT * FROM MyData")
    LiveData<List<MyData>> getAllLiveData();

    @Query("SELECT * FROM MyData WHERE idGame=:id")
    MyData getGameById(long id);

    @Query("UPDATE MyData  SET winner=:winner WHERE idGame=:id")
    void updateGameWinner(long id, String winner);

}
