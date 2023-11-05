package com.ptk.ptk_news.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ptk.ptk_news.R
import com.ptk.ptk_news.viewmodel.NewsFeedViewModel
import ir.kaaveh.sdpcompose.sdp


//UIs
@Composable
fun DetailScreen(
    navController: NavController,
    newsFeedViewModel: NewsFeedViewModel = hiltViewModel(),

    ) {
    val uiStates by newsFeedViewModel.uiStates.collectAsState()


    LaunchedEffect(key1 = Unit) {
//        newsFeedViewModel.getNewsFeed()
    }
    DetailScreenContent()


}

@Composable
fun DetailScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://www.reuters.com/resizer/1VuuhgPwgxldHbkVhHNrYU_AiWY=/1200x628/smart/filters:quality(80)/cloudfront-us-east-2.images.arcpublishing.com/reuters/NACSD6GEZ5L2ZA4AIAZZERRNYM.jpg")
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder),
                contentDescription = "ArticleImage",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.sdp)
                    .clip(RoundedCornerShape(4.sdp))
            )

            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "BackIcon",
                modifier = Modifier
                    .padding(8.sdp)
                    .drawBehind {
                        drawCircle(
                            color = Color.Black,
                            radius = this.size.minDimension,
                            alpha = 0.01F
                        )
                    },
                tint = Color.White
            )
        }
        Text(
            "Title THe polic Brnyr shwe shaung",
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            color = Color.Black,
            fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 8.sdp, top = 8.sdp)
        )

        Text(
            "By Phone Thet Khine",
            fontSize = MaterialTheme.typography.labelSmall.fontSize,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 8.sdp)
                .clip(
                    RoundedCornerShape(8.sdp)
                )
                .background(MaterialTheme.colorScheme.primary)
                .padding(8.sdp)
        )

        Text(
            "Title THe polic Brnyr shwe shaung, asdf tkejrkjekja asdfjaklsdfjalk asdfasdfqwer qerqwerqw qwerlzkjljk qwerpeirpipoi pqeiowrqioweruqiweurasdfjaioesqwerqweruqeiwouiouqiuweoriuqweruqoweruoqieuwroqwueiorquweioruqwoeiur asdcfadsjfkadsjfkasdfajslkdfjlaksjdfaksdjflajsfalsjkdflajfljasdfjalsjdfaksjfkdfj adsfaljdsfklajsdflkajskldfjalkdjsfklajsdkfjalksdjfkj aksjdfkajklsdfjklasdlfjalksdfjalsdflj asdfjalsdjfklajsdfkljalskdfjlaksjdf asdjflakjsdlfkjaklsdfjla;dfajs adsfklajlsdkfjakljfslasjdfl adjsfklajsdlfalsdfj;aljfs aklsdfjlaksdjfalkjsdfaklsdfj Title THe polic Brnyr shwe shaung, asdf tkejrkjekja asdfjaklsdfjalk asdfasdfqwer qerqwerqw qwerlzkjljk qwerpeirpipoi pqeiowrqioweruqiweurasdfjaioesqwerqweruqeiwouiouqiuweoriuqweruqoweruoqieuwroqwueiorquweioruqwoeiur asdcfadsjfkadsjfkasdfajslkdfjlaksjdfaksdjflajsfalsjkdflajfljasdfjalsjdfaksjfkdfj adsfaljdsfklajsdflkajskldfjalkdjsfklajsdkfjalksdjfkj aksjdfkajklsdfjklasdlfjalksdfjalsdflj asdfjalsdjfklajsdfkljalskdfjlaksjdf asdjflakjsdlfkjaklsdfjla;dfajs adsfklajlsdkfjakljfslasjdfl adjsfklajsdlfalsdfj;aljfs aklsdfjlaksdjfalkjsdfaklsdfj",
            fontSize = MaterialTheme.typography.labelSmall.fontSize,
            modifier = Modifier.padding(start = 8.sdp, top = 8.sdp)
        )

    }

}

