package com.example.mynewsapp.data.internal

import com.example.mynewsapp.data.ApiEvent
import com.example.mynewsapp.data.response.NewsAPIResponse
import retrofit2.Response

inline fun safeApiCall(apiCall: () -> Response<NewsAPIResponse>): ApiEvent =
    try {
        val result = apiCall.invoke()
        if (result.isSuccessful) {
            val resultBody = result.body()
            if (resultBody != null && resultBody.articles.isNotEmpty()) {
                ApiEvent.Success(resultBody.articles)
            } else {
                ApiEvent.Empty
            }
        } else {
            ApiEvent.Error("Code: ${result.code()} - Error: ${result.message()} - ${result.errorBody()}")
        }
    } catch (e: Exception) {
        ApiEvent.Error(e.message ?: "An unknown error occurred...")
    }
