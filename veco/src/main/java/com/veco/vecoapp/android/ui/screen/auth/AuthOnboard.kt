package com.veco.vecoapp.android.ui.screen.auth

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.math.MathUtils.clamp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.R
import com.veco.vecoapp.android.ui.navigation.AuthScreen
import com.veco.vecoapp.android.ui.theme.regBody1
import com.veco.vecoapp.android.ui.theme.secondaryBackground
import kotlinx.coroutines.launch

data class Page(
    @StringRes val title: Int,
    @StringRes val description: Int,
    @DrawableRes val image: Int
)

val pages = listOf(
    Page(
        title = MR.strings.onboard_title_1.resourceId,
        description = MR.strings.onboard_desc_1.resourceId,
        image = R.drawable.img_rocket
    ),
    Page(
        title = MR.strings.onboard_title_2.resourceId,
        description = MR.strings.onboard_desc_2.resourceId,
        image = R.drawable.gift_icon
    ),
    Page(
        title = MR.strings.onboard_title_3.resourceId,
        description = MR.strings.onboard_desc_3.resourceId,
        image = R.drawable.img_mag_glass
    ),
    Page(
        title = MR.strings.onboard_title_4.resourceId,
        description = MR.strings.onboard_desc_4.resourceId,
        image = R.drawable.img_book
    )
)

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AuthOnboard(navController: NavHostController) {
    val pagerState = rememberPagerState()
    val localScope = rememberCoroutineScope()

//    val animatedProgress by animateFloatAsState(
//        targetValue = targetPage,
//        animationSpec = tween(
//            durationMillis = 20000
//        ),
//        finishedListener = { page ->
//            localScope.launch {
//                pagerState.animateScrollToPage(page.toInt())
//            }
//        }
//    )

    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            val fPage = page.toFloat()
            animatedProgress.snapTo(fPage)
            localScope.launch {
                val reason = animatedProgress.animateTo(
                    targetValue = fPage + 1,
                    animationSpec = tween(
                        durationMillis = 20000,
                        easing = LinearEasing
                    )
                ).endReason
                if (reason == AnimationEndReason.Finished) {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }
            if (page == 4) {
                navController.navigate(AuthScreen.Home.route)
            }
        }
    }
    Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        repeat(4) {
            StoryBar(
                modifier = Modifier.weight(1f),
                fill = clamp(animatedProgress.value - it, 0f, 1f)
            )
        }
    }
    HorizontalPager(
        count = 5,
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 24.dp),
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        val isLeft = ((it.x) < size.width / 2)
                        val targetPage = pagerState.currentPage + if (isLeft) -1 else 1
                        localScope.launch {
                            pagerState.animateScrollToPage(
                                clamp(
                                    targetPage,
                                    0,
                                    4
                                )
                            )
                        }
                    }
                )
            },
        userScrollEnabled = false,
        itemSpacing = 24.dp
    ) { i ->
        if (i < 4) OnboardContents(pages[i])
    }
}

@Composable
fun OnboardContents(
    page: Page
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(160.dp))
            Image(
                modifier = Modifier.size(156.dp),
                painter = painterResource(id = page.image),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(48.dp))
            Text(text = stringResource(page.title), style = MaterialTheme.typography.h1)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = stringResource(page.description), style = MaterialTheme.typography.regBody1)
        }
    }
}

@Composable
fun StoryBar(modifier: Modifier, fill: Float) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(2.dp))
            .fillMaxWidth()
            .height(4.dp)
            .background(MaterialTheme.colors.secondaryBackground)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(2.dp))
                .background(MaterialTheme.colors.primary)
                .height(4.dp)
                .fillMaxWidth(fill)
        )
    }
}
