package com.veco.vecoapp.android.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.veco.vecoapp.android.ui.component.scaffold.ScaffoldState
import com.veco.vecoapp.android.ui.component.topbar.VecoButtonTopBar
import com.veco.vecoapp.android.ui.component.topbar.VecoExpandableTopBar
import com.veco.vecoapp.android.ui.component.topbar.VecoTopBar
import com.veco.vecoapp.android.ui.component.topbar.topAppBarShadow
import com.veco.vecoapp.android.ui.enums.ToolbarState
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ExperimentalToolbarApi
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@OptIn(ExperimentalToolbarApi::class)
@Composable
fun ExpandableToolbarScaffold(
    modifier: Modifier = Modifier,
    screenTitle: String,
    scaffoldState: ScaffoldState,
    navController: NavController,
    content: @Composable () -> Unit
) {
    val toolbarScaffoldState = rememberCollapsingToolbarScaffoldState()
    LaunchedEffect(scaffoldState) {
        if (scaffoldState.toolbarState == ToolbarState.ExpandableExpanded) toolbarScaffoldState.toolbarState.expand()
    }
    CollapsingToolbarScaffold(
        modifier = modifier
            .fillMaxSize(),
        state = toolbarScaffoldState,
        toolbar = {
            when (scaffoldState.toolbarState) {
                ToolbarState.Collapsed -> {
                    VecoTopBar(
                        navController = navController,
                        title = scaffoldState.screenTitle
                    )
                }
                is ToolbarState.Button -> {
                    VecoButtonTopBar(
                        title = scaffoldState.screenTitle,
                        onClick = scaffoldState.toolbarState.callback
                    )
                }
                ToolbarState.Expandable, ToolbarState.ExpandableExpanded -> {
                    VecoExpandableTopBar(
                        this,
                        toolbarScaffoldState,
                        screenTitle
                    )
                }
                else -> {}
            }
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
