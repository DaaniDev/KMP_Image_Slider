package ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
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
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StatusViewerScreen() {

    val imagesList = listOf(
        Res.drawable.image1,
        Res.drawable.image2,
        Res.drawable.image3,
        Res.drawable.image4,
        Res.drawable.image5
    )

    val pagerState = rememberPagerState(pageCount = { imagesList.size })

    var progress by remember { mutableStateOf(0f) }

    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(pagerState.currentPage){
        progress = 0f
        while(progress <1f){
            delay(50)
            progress+=0.01f
        }
        val nextPage = (pagerState.currentPage + 1) % imagesList.size
       coroutineScope.launch {
           pagerState.animateScrollToPage(nextPage)
       }
    }
    Box(modifier = Modifier.fillMaxSize()) {

        HorizontalPager(state = pagerState) { page ->

            StatusItem(imagesList[page])
        }
    }

    ProgressIndicator(imagesList.size,pagerState.currentPage,progress)
}

@Composable
fun StatusItem(status: DrawableResource) {

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(resource = status),
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun ProgressIndicator(count: Int, currentPage: Int, progress: Float) {

    val indicatorHeight = 8.dp

    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp).height(indicatorHeight),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        repeat(count){ index ->
            Box(modifier = Modifier.weight(1f).height(indicatorHeight).padding(6.dp)){
                val isCurrentPage = index == currentPage

                Canvas(modifier = Modifier.fillMaxSize()){

                    drawLine(color = Color.Gray.copy(alpha = 0.3f),
                        start = Offset(0f,size.height / 2f),
                        end = Offset(size.width, size.height / 2f),
                        strokeWidth = indicatorHeight.toPx(),
                        cap = StrokeCap.Round
                    )

                    if(isCurrentPage){
                        val endX = size.width * progress

                        drawLine(color = Color.Red,
                            start = Offset(0f,size.height / 2f),
                            end = Offset(endX, size.height / 2f),
                            strokeWidth = indicatorHeight.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                }
            }
        }
    }
}