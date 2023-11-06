package com.ptk.ptk_news.ui.ui_resource.composables

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import com.ptk.ptk_news.db.entity.ArticleEntity
import com.ptk.ptk_news.viewmodel.ArticlesViewModel
import com.ptk.ptk_news.viewmodel.NewsFeedViewModel
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

@Composable
fun ReactionBar(
    viewModel: NewsFeedViewModel,
    articlesViewModel: ArticlesViewModel,
    articleEntity: ArticleEntity,
) {
    val context = LocalContext.current

    val scope = rememberCoroutineScope()
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        CustomIcon(
            imageVector = Icons.Filled.Favorite,
            tint = if (articleEntity.isFav) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
            cd = "FavIcon"
        ) {
            scope.launch {
                articleEntity.isFav = !articleEntity.isFav
                viewModel.updateIsFav(articleEntity)
            }
        }

        CustomIcon(
            imageVector = Icons.Filled.Comment,
            MaterialTheme.colorScheme.onPrimary,
            cd = "CommentIcon"
        ) {
            viewModel.toggleCommentBoxDialog(true, articleEntity.id)

        }


        CustomIcon(
            imageVector = Icons.Filled.Bookmark,
            tint = if (articleEntity.isBookMark) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
            cd = "BookMarkIcon"
        ) {
            scope.launch {
                if (!articleEntity.isBookMark) {
                    articleEntity.isBookMark = true
                    viewModel.insertBookMark(articleEntity)
                } else {
                    articleEntity.isBookMark = false
                    viewModel.removeBookMark(articleEntity)
                    articlesViewModel.getBookMarkArticles()

                }
            }
        }

        CustomIcon(
            imageVector = Icons.Filled.Share,
            tint = MaterialTheme.colorScheme.onPrimary,
            cd = "ShareIcon"
        ) {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "${articleEntity.url}")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            context.startActivity(shareIntent)
        }
    }
}

@Composable
fun CustomIcon(imageVector: ImageVector, tint: Color, cd: String, onClick: () -> Unit) {
    Icon(
        imageVector = imageVector,
        contentDescription = cd,
        modifier = Modifier
            .padding(16.sdp)
            .size(15.sdp)
            .clickable {
                onClick.invoke()
            }
            .drawBehind {
                drawCircle(
                    color = Color.Black,
                    radius = this.size.minDimension,
                    alpha = 0.25F
                )
            },
        tint = tint
    )
}