package com.example.share.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.share.data.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val articleInteractor: NewsRepository
) : ViewModel() {
    // TODO: Implement the ViewModel

    fun onDeleteAllBookmarks() {
        viewModelScope.launch {
            articleInteractor.resetAllBookmarks()
            Timber.d("BookMark Articles has been removed")
        }
    }

    fun onDeleteNonBookmarkedArticles() {
        viewModelScope.launch {
            articleInteractor.deleteNonBookmarkedArticles()
            Timber.d("Non BookMark Articles has been removed")
        }
    }
}