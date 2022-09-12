package com.example.share.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [NewsArticle::class, BreakingNews::class],
    version = 1
)
abstract class NewsArticleDB : RoomDatabase() {
    abstract fun newsArticleDao(): NewsArticleDao
}