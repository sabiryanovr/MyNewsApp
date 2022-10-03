package com.example.share.presentation.breakingnews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.share.data.NewsArticle
import com.example.share.domain.ArticleInteractor
import com.example.share.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class BreakingViewModel @Inject constructor(
    private val articleInteractor: ArticleInteractor
) : ViewModel() {
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
            _uiStateLiveData.value = UiStateView.Loading
            try {
                var updatedAtNews: Long? = null
                try{
                    updatedAtNews = articleInteractor.getUpdatedAtNewsArticle()
                } catch (t: Throwable) {
                }
                val needsRefresh = updatedAtNews == null ||
                        updatedAtNews < System.currentTimeMillis() -
                        TimeUnit.MINUTES.toMillis(60)

                val result = articleInteractor.getBreakingNews(needsRefresh)
                result
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

    init {
        viewModelScope.launch {
            articleInteractor.deleteNonBookmarkedArticlesOlderThan(
                System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7)
            )
        }
    }


    fun onManualRefresh() {
        refresh()
    }

    fun onBookmarkClick(article: NewsArticle) {
        val currentlyBookmarked = article.isBookmarked
        val updatedArticle = article.copy(isBookmarked = !currentlyBookmarked)
        viewModelScope.launch {
            articleInteractor.updateArticle(updatedArticle)
        }.invokeOnCompletion { updateBreakingNews() }
    }

    fun updateBreakingNews() {
        viewModelScope.launch {
            try {
                val result = articleInteractor.getBreakingNews(true)
                result
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

