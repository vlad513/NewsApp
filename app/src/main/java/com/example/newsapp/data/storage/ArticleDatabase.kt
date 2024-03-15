package com.example.newsapp.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapp.data.models.Article

@Database(entities = [Article::class], version = 1 , exportSchema = true)
abstract class ArticleDatabase :RoomDatabase(){

    abstract fun getArticleDao():ArticleDao

}