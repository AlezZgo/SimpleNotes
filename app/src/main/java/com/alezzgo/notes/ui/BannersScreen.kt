package com.alezzgo.notes.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.*

@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun BannersScreen() {


    ViewPagerCompose()


}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ViewPagerCompose() {
    val pages = listOf("Page 1", "Page 2", "Page 3", "Page 4", "Page 5")

    val pagerState = rememberPagerState(initialPage = 0)
    val scope = rememberCoroutineScope()

    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(key1 = pagerState, key2 = interactionSource) {
        scope.launch {
            while (true) {
                delay(5000) // ждать 5 секунд

                // Прокрутить до следующей страницы, вернуться к началу, если достигнут конец
                if (pagerState.currentPage < (pagerState.pageCount - 1)) {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                } else {
                    pagerState.animateScrollToPage(0)
                }
            }

        }
        interactionSource.interactions.collect {
            when (it) {
                is PressInteraction.Press -> {
                    scope.cancel()
                }
                is PressInteraction.Release -> {
                    scope.launch {
                        while (true) {
                            delay(5000) // ждать 5 секунд

                            // Прокрутить до следующей страницы, вернуться к началу, если достигнут конец
                            if (pagerState.currentPage < (pagerState.pageCount - 1)) {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            } else {
                                pagerState.animateScrollToPage(0)
                            }
                        }
                    }
                }
                else -> {}
            }
        }
    }

    Box(modifier = Modifier.fillMaxWidth().height(100.dp)) {
        HorizontalPager(
            state = pagerState,
            count = pages.size,
            modifier = Modifier.padding(16.dp)
        ) { page ->
            // This is your page content
            ElevatedCard(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .aspectRatio(1f),
                shape = RoundedCornerShape(16.dp),
            ) {
                Text(
                    text = pages[page],
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        // Add pager indicator
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            activeColor = MaterialTheme.colors.secondary,
            inactiveColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
        )
    }
}