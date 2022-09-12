package com.example.share.presentation.bookmarks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.share.data.NewsArticleDB
import com.example.share.domain.ArticleInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val articleInteractor: ArticleInteractor
) :
    ViewModel() {
    private var _articles: MutableLiveData<List<NewsArticleDB>> = MutableLiveData()
    val articles: LiveData<List<NewsArticleDB>> get() = _articles

    init {
        viewModelScope.launch {
            articleInteractor.getAllBookmarkedArticles()
        }
    }

}