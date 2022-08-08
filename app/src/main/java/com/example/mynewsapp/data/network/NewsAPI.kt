package com.example.mynewsapp.data.network

import com.example.mynewsapp.Constants
import com.example.mynewsapp.data.response.NewsAPIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("top-headlines")
    suspend fun getArticles(
        @Query("page") page: Int = Constants.DEFAULT_PAGE,
        @Query("pageSize") pageSize: Int = Constants.DEFAULT_PAGE_SIZE,
        @Query("country") countryCode: String = Constants.DEFAULT_COUNTRY_CODE,
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ): Response<NewsAPIResponse>

}