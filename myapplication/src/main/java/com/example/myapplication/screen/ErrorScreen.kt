package com.example.myapplication.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    message: String,
    refresh: () -> Unit
) {
    Box() {
        Column(modifier = modifier.align(Alignment.Center)) {
            Text(
                text = message,
                style = MaterialTheme.typography.displaySmall,
                color = Color(0xff990000),
                modifier = modifier.align(Alignment.CenterHorizontally)
            )
            Button(
                onClick = { refresh() },
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 8.dp)
            ) {
                Text(text = "Refresh")
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewErrorScreen() {
    ErrorScreen(message = "Error") {

    }
}