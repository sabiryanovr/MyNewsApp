package com.example.myapplication.screen

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.share.presentation.breakingnews.BreakingViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.viewinterop.AndroidView
import com.example.share.data.NewsArticle
import androidx.activity.ComponentActivity
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp


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
            Snackbar{
                Text(uiState.throwable.message ?: "Error", fontSize = 22.sp)
            }
//            ErrorScreen(message = uiState.throwable.message ?: "Error") {
//               viewModel.refresh()
//           }
        }
        BreakingViewModel.UiStateView.Loading -> {
            LoadingScreen(modifier = modifier)
        }
        null -> {}
    }
}

@Composable
fun BreakingNews(modifier: Modifier, news: List<NewsArticle>, viewModel: BreakingViewModel) {

    LazyColumn {
        items(news) { article ->
            NewsItem(article = article, modifier = modifier, onBookmarkClick = {viewModel.onBookmarkClick(article)}, onItemClick = { previewItem(article.url) })
        }
    }
}


private fun previewItem(url: String) {
    val uri = Uri.parse(url)
/*    val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        intent
        startActivityForResult(intent, 1)
    }

 */
/*
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    }, update = {
        it.loadUrl(url)
    })
    */

}
