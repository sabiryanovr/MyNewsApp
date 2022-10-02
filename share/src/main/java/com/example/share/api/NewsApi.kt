package com.example.share.api

import com.example.imdb.networking.ApiResponse
import com.example.share.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsApi {
    companion object {
        const val BASE_URL = Constants.BASE_URL
        const val API_KEY = Constants.API_KEY
    }
    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines?country=ru")
    suspend fun getBreakingNews(
    ): NewsResponse
}