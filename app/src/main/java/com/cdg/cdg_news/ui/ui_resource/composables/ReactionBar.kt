package com.cdg.cdg_news.ui.ui_resource.composables

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.cdg.cdg_news.db.entity.ArticleEntity
import ir.kaaveh.sdpcompose.sdp

@Composable
fun ReactionBar(
    articleEntity: ArticleEntity,
    favOnClick: () -> Unit,
    commentOnClick: () -> Unit,
    bookMarkOnClick: () -> Unit,
    shareOnClick: () -> Unit,
) {

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

            favOnClick.invoke()
        }

        CustomIcon(
            imageVector = Icons.Filled.Comment,
            MaterialTheme.colorScheme.onPrimary,
            cd = "CommentIcon"
        ) {
            commentOnClick.invoke()
        }


        CustomIcon(
            imageVector = Icons.Filled.Bookmark,
            tint = if (articleEntity.isBookMark) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
            cd = "BookMarkIcon"
        ) {

            bookMarkOnClick.invoke()
        }

        CustomIcon(
            imageVector = Icons.Filled.Share,
            tint = MaterialTheme.colorScheme.onPrimary,
            cd = "ShareIcon"
        ) {
            shareOnClick.invoke()

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