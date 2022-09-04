package com.example.pmu_monopol.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {MyData.class,GameStep.class}, version = 3, exportSchema = false)
public abstract class MyDatabase extends  RoomDatabase{

    public abstract MyDataDao dataDao();
    public abstract  GameStepDao gameStepDao();

    private static final String DATABASE_NAME = "game_database.db";
    private static MyDatabase instance = null;

    public static MyDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (MyDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            MyDatabase.class,
                            DATABASE_NAME)
                            .build();
                }
            }
        }
        return instance;
    }
}
