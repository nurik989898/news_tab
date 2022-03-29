package com.example.news_tab;

import android.app.Application;

import androidx.room.Room;

import com.example.news_tab.room.AppDatabase;

public class App extends Application {
    private static AppDatabase database;
    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(this,AppDatabase.class,"database")
                .allowMainThreadQueries()
                .build();
    }

    public static AppDatabase getDatabase() {
        return database;
    }
}
