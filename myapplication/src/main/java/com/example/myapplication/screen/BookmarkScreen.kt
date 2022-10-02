package com.example.myapplication.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.share.data.NewsArticle
import com.example.share.presentation.bookmarks.BookmarksViewModel

@Composable
fun BookmarkScreen(
    viewModel: BookmarksViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val state = viewModel.uiStateLiveData.observeAsState()
    when (val uiState = state.value) {
        is BookmarksViewModel.UiStateView.Data -> {
            viewModel.updateBookmarksNews()
            BookmarksNews(modifier, uiState.news, viewModel)
        }
        is BookmarksViewModel.UiStateView.Error -> {
            ErrorScreen(message = uiState.throwable.message ?: "Error") {
                viewModel.refresh()
            }
        }
        BookmarksViewModel.UiStateView.Loading -> {
        }
        null -> {}
    }
}

@Composable
fun BookmarksNews(modifier: Modifier, news: List<NewsArticle>, viewModel: BookmarksViewModel) {

    LazyColumn {
        items(news) { article ->
            NewsItem(article = article, modifier = modifier, {viewModel.onBookmarkClick(article)})
        }
    }
}