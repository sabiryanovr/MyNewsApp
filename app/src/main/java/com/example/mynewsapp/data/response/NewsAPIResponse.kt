package com.example.mynewsapp.data.response

import com.example.mynewsapp.data.model.Article
import com.google.gson.annotations.SerializedName

data class NewsAPIResponse(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)
