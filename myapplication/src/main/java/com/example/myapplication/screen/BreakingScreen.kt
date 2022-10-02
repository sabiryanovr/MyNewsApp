package com.example.myapplication.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.share.presentation.breakingnews.BreakingViewModel
import androidx.compose.runtime.livedata.observeAsState
import com.example.share.data.NewsArticle

@Composable
fun BreakingScreen(
    viewModel: BreakingViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val state = viewModel.uiStateLiveData.observeAsState()
    when (val uiState = state.value) {
        is BreakingViewModel.UiStateView.Data -> {
            viewModel.updateBreakingNews()
            BreakingNews(modifier, uiState.news, viewModel)
        }
        is BreakingViewModel.UiStateView.Error -> {
            ErrorScreen(message = uiState.throwable.message ?: "Error") {
                viewModel.refresh()
            }
        }
        BreakingViewModel.UiStateView.Loading -> {
        }
        null -> {}
    }
}

@Composable
fun BreakingNews(modifier: Modifier, news: List<NewsArticle>, viewModel: BreakingViewModel) {

    LazyColumn {
        items(news) { article ->
            NewsItem(article = article, modifier = modifier, {viewModel.onBookmarkClick(article)})
        }
    }
}
