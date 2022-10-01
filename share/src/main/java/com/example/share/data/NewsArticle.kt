package com.example.share.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_articles")

data class NewsArticle(
    val title: String?,
    val publishedAt: String?,
    @PrimaryKey val url: String,
    val thumbnailUrl: String?,
    val isBookmarked: Boolean,
    val updatedAt: Long = System.currentTimeMillis()
)
