package com.veco.vecoapp.android.ui.component.scaffold

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState

@Composable
fun VecoSwipeRefresh(
    progressState: MutableState<Float>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    val swipeRefreshState = remember { SwipeRefreshState(isRefreshing = isRefreshing) }
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = onRefresh,
        indicator = { state, triggerDp ->
            if (!state.isRefreshing) {
                val triggerPx = with(LocalDensity.current) { triggerDp.toPx() }
                val progress = (state.indicatorOffset / triggerPx).coerceIn(0f, 1f)
                progressState.value = progress
            }
        },
        content = content
    )
}
