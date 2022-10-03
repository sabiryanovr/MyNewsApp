package com.example.myapplication.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.R
import com.example.share.presentation.setting.SettingViewModel

@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
            .wrapContentSize(Alignment.TopCenter)
    ) {
        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            onClick = { viewModel.onDeleteAllBookmarks() },
        ) {
            Text (text = stringResource(R.string.delete_all_bookmarks), color = Color.White)
        }
        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            onClick = { viewModel.onDeleteNonBookmarkedArticles() },
        ) {
            Text (text = stringResource(R.string.delete_bookmarks_news), color = Color.White)
        }
        Text(
            text = stringResource(R.string.text_delete_non_bookmarks_news),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
        )
    }
}
@Preview(showBackground = true)
@Composable
fun SettingPreview() {
    SettingScreen()
}