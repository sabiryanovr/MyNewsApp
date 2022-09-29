package com.example.share.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsArticleDao {
    @Query("SELECT * FROM news_articles")
    fun getAllBreakingNewsArticles(): Flow<List<NewsArticle>>


    @Query("SELECT * FROM news_articles WHERE isBookmarked = 1")
    fun getAllBookmarkedArticles(): Flow<List<NewsArticle>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<NewsArticle>)

    @Update
    suspend fun updateArticle(article: NewsArticle)

    @Query("UPDATE news_articles SET isBookmarked = 0")
    suspend fun resetAllBookmarks()


    @Query("DELETE FROM news_articles")
    suspend fun deleteAllBreakingNews()

    @Query("DELETE FROM news_articles WHERE isBookmarked = 0")
    suspend fun deleteNonBookmarkedArticles()


    @Query("DELETE FROM news_articles WHERE updatedAt < :timestampInMillis AND isBookmarked = 0")
    suspend fun deleteNonBookmarkedArticlesOlderThan(timestampInMillis: Long)
}