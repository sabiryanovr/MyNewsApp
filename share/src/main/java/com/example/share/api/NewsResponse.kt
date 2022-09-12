package com.example.share.api

data class NewsResponse(val articles: List<NewsArticle>)

data class NewsArticle(
    val title: String?,
    val url: String,
    val urlToImage: String?
)
