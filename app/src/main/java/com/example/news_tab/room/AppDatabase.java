package com.example.news_tab.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.news_tab.models.News;

@Database(entities = (News.class), version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();
}
