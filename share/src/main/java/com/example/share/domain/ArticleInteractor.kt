package com.example.share.domain

import com.example.share.data.NewsArticle
import com.example.share.data.NewsRepository
import com.example.share.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticleInteractor @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend fun getBreakingNews(
        forceRefresh: Boolean,
        onFetchSuccess: () -> Unit,
        onFetchFailed: (Throwable) -> Unit): Flow<Resource<List<NewsArticle>>> {
        return newsRepository.getBreakingNews(forceRefresh, onFetchSuccess, onFetchFailed)
    }

    fun getAllBookmarkedArticles(): Flow<List<NewsArticle>> {
        return newsRepository.getAllBookmarkedArticles()
    }

    suspend fun updateArticle(article: NewsArticle) {
        newsRepository.updateArticle(article)
    }

    suspend fun resetAllBookmarks() {
        newsRepository.resetAllBookmarks()
    }

    suspend fun deleteNonBookmarkedArticlesOlderThan(timestampInMillis: Long) {
        newsRepository.deleteNonBookmarkedArticlesOlderThan(timestampInMillis)
    }
}