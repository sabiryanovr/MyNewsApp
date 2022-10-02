package com.example.share.data

import androidx.room.withTransaction
import com.example.share.api.NewsApi
import com.example.share.util.Resource
import com.example.share.util.networkBoundResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

class NewsRepository  @Inject constructor(
    private val newsApi: NewsApi,
    private val newsArticleDb: NewsArticleDB,
//    private val backgroundDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val newsArticleDao = newsArticleDb.newsArticleDao()

    suspend fun getBreakingNews(forceCache: Boolean): Result<List<NewsArticle>> =
        withContext(Dispatchers.IO) {
            if (forceCache) {
                val breakingCache = newsArticleDao.getAllBreakingNewsArticles()
                if (breakingCache.size != 0){
                    return@withContext Result.success(breakingCache)
                }
            }
            val response = newsApi.getBreakingNews()
            try{
                val bookmarkedArticles = newsArticleDao.getAllBookmarkedArticles()
                val newsArticles = response.articles.map {
                        serverBreakingNewsArticle ->

                    val isBookmarked = bookmarkedArticles.any { bookmarkedArticle ->
                        bookmarkedArticle.url == serverBreakingNewsArticle.url
                    }

                    NewsArticle(
                        title = serverBreakingNewsArticle.title,
                        publishedAt = serverBreakingNewsArticle.publishedAt,
                        url = serverBreakingNewsArticle.url,
                        thumbnailUrl = serverBreakingNewsArticle.urlToImage,
                        isBookmarked = isBookmarked
                    )
               }
                newsArticleDb.withTransaction {
                    newsArticleDao.insertArticles(newsArticles)
                }
                val breakingCache = newsArticleDao.getAllBreakingNewsArticles()
                if (breakingCache.size != 0) {
                    return@withContext Result.success(breakingCache)
                }

            } catch (e: Throwable) {

            }
            error("ApiResponse return is not valid")
        }



    suspend fun updateArticle(article: NewsArticle) {
        newsArticleDao.updateArticle(article)
    }

    suspend fun resetAllBookmarks() {
        newsArticleDao.resetAllBookmarks()
    }

    suspend fun deleteNonBookmarkedArticles() {
        newsArticleDao.deleteNonBookmarkedArticles()
    }

    suspend fun deleteNonBookmarkedArticlesOlderThan(timestampInMillis: Long) {
        newsArticleDao.deleteNonBookmarkedArticlesOlderThan(timestampInMillis)
    }
}