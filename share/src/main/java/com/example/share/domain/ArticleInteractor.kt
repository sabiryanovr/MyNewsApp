package com.example.share.domain

import com.example.share.data.NewsArticle
import com.example.share.data.NewsRepository
import com.example.share.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticleInteractor @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend fun getBreakingNews( forceCache: Boolean): Result<List<NewsArticle>> =
        newsRepository.getBreakingNews(forceCache)

    suspend fun getAllBookmarkedArticles(): Result<List<NewsArticle>> {
        val boomarksNews = newsRepository.getBreakingNews(true).map {
            news -> news.filter {
                it.isBookmarked
            }
        }
        return boomarksNews
    }

    suspend fun updateArticle(article: NewsArticle) {
        newsRepository.updateArticle(article)
    }

    suspend fun resetAllBookmarks() {
        newsRepository.resetAllBookmarks()
    }

    suspend fun deleteNonBookmarkedArticles() {
        newsRepository.deleteNonBookmarkedArticles()
    }

    suspend fun deleteNonBookmarkedArticlesOlderThan(timestampInMillis: Long) {
        newsRepository.deleteNonBookmarkedArticlesOlderThan(timestampInMillis)
    }

    suspend fun getUpdatedAtNewsArticle(): Long {
        return newsRepository.getUpdatedAtNewsArticle()
    }
}