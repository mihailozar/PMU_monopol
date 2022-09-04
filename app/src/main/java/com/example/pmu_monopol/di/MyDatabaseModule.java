package com.example.pmu_monopol.di;

import android.content.Context;

import com.example.pmu_monopol.data.MyData;
import com.example.pmu_monopol.data.MyDataDao;
import com.example.pmu_monopol.data.MyDatabase;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public interface MyDatabaseModule {
    @Provides
    static MyDataDao provideDataDao(@ApplicationContext Context context){
        return MyDatabase.getInstance( context).dataDao();
    }
}
