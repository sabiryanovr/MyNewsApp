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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.share.data.NewsArticle
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.ui.theme.Typography

@Composable
fun NewsItem(
    article: NewsArticle,
    modifier: Modifier = Modifier,
    onBookmarkClick: () -> Unit,
    onItemClick: () -> Unit

) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Column(
            modifier = modifier
                .weight(1f, fill = true)
                .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
        ) {
            AsyncImage(
                model = article.thumbnailUrl,
                contentDescription = null,
                modifier = modifier
                    .height(180.dp)
                    .padding(top = 4.dp, bottom = 4.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = com.example.myapplication.R.drawable.image_placeholder)
            )
            Text(
                text = article.title!!,
                style = Typography.titleMedium
            )
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
                ){
                Text(
                    text = dateFormat(article.publishedAt!!),
                    style = Typography.labelSmall
                )
                Button(
                    onClick =  onItemClick,
                ) {
                    Text(text = stringResource(R.string.previewArticle))
                }
                Button(
                    onClick =  onBookmarkClick,
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
}

private fun dateFormat(str: String): String {
    return str.substring(8,10) + "/" + str.substring(5,7) + "/" + str.substring(0,4)
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
        onBookmarkClick = {},
        onItemClick = {}
    )
}