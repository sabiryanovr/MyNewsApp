package com.example.mynewsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mynewsapp.Constants
import com.example.mynewsapp.data.ApiEvent
import com.example.mynewsapp.data.model.Article
import com.example.mynewsapp.data.network.NewsAPI
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(private val api: NewsAPI) {

    suspend fun getBreakingNewsArticles(): List<Article>? {
        val result = api.getArticles()
        val resultBody = result.body()
        if (resultBody != null) {
            return resultBody.articles
        } else return null

    }

}