package com.example.myapplication.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.share.data.NewsArticle
import coil.compose.AsyncImage
import com.example.myapplication.ui.theme.Typography

@Composable
fun NewsItem(
    article: NewsArticle,
    modifier: Modifier = Modifier,
    onBookmarkClick: () -> Unit

) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        AsyncImage(
            model = article.thumbnailUrl,
            contentDescription = null,
            modifier = modifier
                .padding(16.dp)
                .width(124.dp)
                .height(124.dp),
            placeholder = painterResource(id = com.example.myapplication.R.drawable.image_placeholder)
        )
        Column(
            modifier = modifier
                .weight(1f, fill = true)
                .padding(end = 16.dp),
        ) {
            Text(
                text = article.title!!,
                style = Typography.titleMedium
            )
            Text(
                text = article.publishedAt!!,
                style = Typography.titleMedium
            )
            Button(
                onClick =  onBookmarkClick,
                modifier = modifier
                    .align(alignment = Alignment.End),
            ) {
                if (article.isBookmarked)
                    Image(
                        painter = painterResource(id = com.example.myapplication.R.drawable.ic_bookmark_selected),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = Color.White)
                    )
                else
                    Image(
                        painter = painterResource(id = com.example.myapplication.R.drawable.ic_bookmark_unselected),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = Color.White)
                    )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsArticlePreview() {
    NewsItem(
        NewsArticle(
            title = "Title",
            publishedAt = "02/10/2022",
            isBookmarked = false,
            url = "https://ya.ru",
            thumbnailUrl = "https://ss.sport-express.ru/userfiles/materials/182/1822831/large.jpg"
        ),
        onBookmarkClick = {}
    )
}