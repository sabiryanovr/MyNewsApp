package com.example.share.presentation.bookmarks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.share.data.NewsArticle
import com.example.share.domain.ArticleInteractor
import com.example.share.presentation.breakingnews.BreakingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val articleInteractor: ArticleInteractor
) :
    ViewModel() {
    sealed class UiStateView {
        data class Data(val news: List<NewsArticle>) : UiStateView()
        object Loading : UiStateView()
        class Error(val throwable: Throwable) : UiStateView()
    }

    private val _uiStateLiveData: MutableLiveData<UiStateView> =
        MutableLiveData(UiStateView.Loading)
    val uiStateLiveData: LiveData<UiStateView> get() = _uiStateLiveData

    init {
        refresh()
    }
    fun refresh() {
        viewModelScope.launch {
            try {
                val bookmarks = articleInteractor.getAllBookmarkedArticles()
                bookmarks
                    .onSuccess { _uiStateLiveData.value = UiStateView.Data(it) }
                    .onFailure {
                        _uiStateLiveData.value = UiStateView.Error(it)
                        Timber.w(it)
                    }
            } catch (t: Throwable) {
                _uiStateLiveData.value = UiStateView.Error(t)
                Timber.w(t)
            }
        }
    }

    fun onBookmarkClick(article: NewsArticle) {
        val currentlyBookmarked = article.isBookmarked
        val updatedArticle = article.copy(isBookmarked = !currentlyBookmarked)
        viewModelScope.launch {
            articleInteractor.updateArticle(updatedArticle)
        }.invokeOnCompletion { updateBookmarksNews() }
    }

    fun onDeleteAllBookmarks() {
        viewModelScope.launch {
            articleInteractor.resetAllBookmarks()
        }.invokeOnCompletion { updateBookmarksNews() }
    }

    fun updateBookmarksNews() {
        viewModelScope.launch {
            try {
                val bookmarks = articleInteractor.getAllBookmarkedArticles()
                bookmarks
                    .onSuccess { _uiStateLiveData.value = UiStateView.Data(it) }
                    .onFailure {
                        _uiStateLiveData.value = UiStateView.Error(it)
                        Timber.w(it)
                    }
            } catch (t: Throwable) {
                _uiStateLiveData.value = UiStateView.Error(t)
                Timber.w(t)
            }
        }
    }
}