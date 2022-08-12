package com.veco.vecoapp.android.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.unit.dp
import com.veco.vecoapp.android.ui.ScaffoldState
import com.veco.vecoapp.android.ui.component.topbar.VecoExpandableTopBar
import com.veco.vecoapp.android.ui.component.topbar.topAppBarShadow
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun ExpandableToolbarScaffold(
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState,
    content: @Composable () -> Unit
) {
    val toolbarScaffoldState = rememberCollapsingToolbarScaffoldState()
    CollapsingToolbarScaffold(
        modifier = modifier
            .fillMaxSize(),
        state = toolbarScaffoldState,
        toolbar = {
            VecoExpandableTopBar(
                this,
                toolbarScaffoldState,
                scaffoldState.screenTitle
            )
        },
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbarModifier = Modifier.drawBehind {
            topAppBarShadow(this, toolbarScaffoldState.toolbarState.progress)
        }
    ) {
        Spacer(modifier = Modifier.size(0.dp)) // hack to avoid scaffold crash on empty body
        content()
    }
}
