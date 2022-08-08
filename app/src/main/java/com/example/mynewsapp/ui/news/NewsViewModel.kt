package com.example.mynewsapp.ui.news

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mynewsapp.data.model.Article
import com.example.mynewsapp.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: AppRepository,
) : ViewModel() {

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles


    init {

        getAllArticles()

    }


    private fun getAllArticles() = viewModelScope.launch {
        _articles.value = repository.getBreakingNewsArticles()
    }

}