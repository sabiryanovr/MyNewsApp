package com.example.share.api

import java.io.Serializable

data class NewsResponse(val articles: List<NewsArticle>)

data class NewsArticle(
    val title: String?,
    val publishedAt: String?,
    val url: String,
    val urlToImage: String?
)
