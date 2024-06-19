package ui.screens

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kmpimageslider.composeapp.generated.resources.Res
import kmpimageslider.composeapp.generated.resources.image1
import kmpimageslider.composeapp.generated.resources.image2
import kmpimageslider.composeapp.generated.resources.image3
import kmpimageslider.composeapp.generated.resources.image4
import kmpimageslider.composeapp.generated.resources.image5
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import kotlin.math.absoluteValue
import kotlin.math.min

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageSliderScreen() {

    val imagesList = listOf(
        Res.drawable.image1,
        Res.drawable.image2,
        Res.drawable.image3,
        Res.drawable.image4,
        Res.drawable.image5
    )

    val pageState = rememberPagerState(pageCount = { imagesList.size })

    val coroutineScope = rememberCoroutineScope()

    // Auto Scroll for View Pager

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            val nextPage = (pageState.currentPage+1) % imagesList.size
            coroutineScope.launch {
                pageState.animateScrollToPage(nextPage)
            }
        }
    }

    Box(modifier = Modifier.wrapContentSize(), contentAlignment = Alignment.Center) {
        HorizontalPager(state = pageState) { page ->
            val actualPage = page % imagesList.size

            Card(modifier = Modifier.fillMaxWidth().height(300.dp).graphicsLayer {
                val pageOffset = pageState.offsetForPage(page)
                val offScreenRight = pageOffset < 0f
                val deg = 105f
                val interpolated = FastOutLinearInEasing.transform(pageOffset.absoluteValue)
                rotationY = min(interpolated * if (offScreenRight) deg else -deg, 90f)

                transformOrigin = TransformOrigin(
                    pivotFractionX = if (offScreenRight) 0f else 1f,
                    pivotFractionY = .5f
                )

            }) {
                Image(
                    painter = painterResource(resource = imagesList[actualPage]),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }

        }

        Row(
            modifier = Modifier.wrapContentHeight().fillMaxWidth().align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center
        ) {

            repeat(imagesList.size) { iteration ->
                val color =
                    if ((pageState.currentPage % imagesList.size) == iteration) Color.Black else Color.LightGray
                Box(
                    modifier = Modifier.padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(16.dp)
                )

            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun PagerState.offsetForPage(page: Int) = (currentPage - page) + currentPageOffsetFraction