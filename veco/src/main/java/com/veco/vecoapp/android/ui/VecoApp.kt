package com.veco.vecoapp.android.ui

import android.os.Parcelable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.veco.vecoapp.android.ui.navigation.VecoBottomNavigation
import com.veco.vecoapp.android.ui.navigation.VecoNavGraph
import com.veco.vecoapp.android.ui.navigation.VecoNavigationProvider
import com.veco.vecoapp.android.ui.theme.VecoAppTheme
import kotlinx.parcelize.Parcelize
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Parcelize
data class ScaffoldState(
    var screenTitle: String,
    var isNavigationVisible: Boolean
) : Parcelable

@Composable
fun VecoApp() {
    VecoAppTheme {
        val navController = rememberNavController()
        val navigationProvider = VecoNavigationProvider(navController)
        val scaffoldState = rememberSaveable {
            mutableStateOf(ScaffoldState("", true))
        }
        Scaffold(
            bottomBar = {
                AnimatedVisibility(
                    visible = scaffoldState.value.isNavigationVisible,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it }),
                    content = {
                        VecoBottomNavigation(navigationProvider)
                    }
                )
            },
            content = {
                val toolbarScaffoldState = rememberCollapsingToolbarScaffoldState()
                CollapsingToolbarScaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    state = toolbarScaffoldState,
                    toolbar = {
                        VecoTopAppBar(
                            this,
                            toolbarScaffoldState,
                            scaffoldState.value.screenTitle
                        )
                    },
                    scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
                    toolbarModifier = Modifier.drawBehind {
                        topAppBarShadow(this, toolbarScaffoldState.toolbarState.progress)
                    }
                ) {
                    Spacer(modifier = Modifier.size(0.dp)) // hack to avoid scaffold crash on empty body
                    VecoNavGraph(
                        navController = navController,
                        scaffoldState = scaffoldState
                    )
                }
            }
        )
    }
}