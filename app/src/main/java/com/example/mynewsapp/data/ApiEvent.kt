package com.example.mynewsapp.data

import com.example.mynewsapp.data.model.Article

sealed class ApiEvent {
    class Success(val data: List<Article>) : ApiEvent()
    class Error(val message: String) : ApiEvent()
    object Empty : ApiEvent()
    object Loading : ApiEvent()
}
