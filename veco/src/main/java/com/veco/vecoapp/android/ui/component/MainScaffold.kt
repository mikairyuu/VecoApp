package com.veco.vecoapp.android.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.veco.vecoapp.android.ui.SheetSettings
import com.veco.vecoapp.android.ui.component.topbar.VecoButtonTopBar
import com.veco.vecoapp.android.ui.component.topbar.VecoTopBar
import com.veco.vecoapp.android.ui.enums.ToolbarState
import com.veco.vecoapp.android.ui.navigation.VecoBottomNavigation

data class ScaffoldState(
    val screenTitle: String,
    val navigationVisible: Boolean,
    val toolbarState: ToolbarState
)

@Composable
fun MainScaffold(
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    bottomSheetState: SheetSettings? = null,
    content: @Composable () -> Unit
) {
    BottomSheetScaffold(bottomSheetState) {
        Scaffold(
            // If topbar is expandable, we handle it in expandable scaffold instead
            topBar = {
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
                    else -> {}
                }
            },
            bottomBar = {
                AnimatedVisibility(
                    visible = scaffoldState.navigationVisible,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it }),
                    content = {
                        VecoBottomNavigation(
                            navController = navController
                        )
                    }
                )
            },
            content = {
                if (scaffoldState.toolbarState == ToolbarState.Expandable) {
                    ExpandableToolbarScaffold(
                        modifier = Modifier.padding(it),
                        screenTitle = scaffoldState.screenTitle,
                        content = content
                    )
                } else {
                    content()
                }
            }
        )
    }
}
