package com.example.news_tab.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.news_tab.models.News;

import java.util.List;

@Dao
public interface NewsDao {
    @Query("SELECT * FROM news")
    List<News> getAll();

    @Insert
    void insert(News news);
}
