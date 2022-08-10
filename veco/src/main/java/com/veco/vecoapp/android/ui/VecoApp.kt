package com.veco.vecoapp.android.ui

import android.os.Parcelable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.veco.vecoapp.android.ui.component.ExpandableToolbarScaffold
import com.veco.vecoapp.android.ui.component.topbar.VecoTopBar
import com.veco.vecoapp.android.ui.enums.ToolbarState
import com.veco.vecoapp.android.ui.navigation.VecoBottomNavigation
import com.veco.vecoapp.android.ui.navigation.VecoNavGraph
import com.veco.vecoapp.android.ui.navigation.VecoNavigationProvider
import com.veco.vecoapp.android.ui.theme.VecoAppTheme
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScaffoldState(
    var screenTitle: String,
    var navigationVisible: Boolean,
    var topBarExpandable: ToolbarState,
) : Parcelable

@Composable
fun VecoApp() {
    VecoAppTheme {
        val navController = rememberNavController()
        val navigationProvider = VecoNavigationProvider(navController)
        val systemUiController = rememberSystemUiController()
        systemUiController.setSystemBarsColor(
            color = Color.White
        )
        val scaffoldState = rememberSaveable {
            mutableStateOf(ScaffoldState("", true, ToolbarState.None))
        }
        val navGraph: @Composable () -> Unit = {
            VecoNavGraph(
                navController = navController,
                scaffoldState = scaffoldState
            )
        }
        Scaffold(
            // If topbar is expandable, we handle it in expandable scaffold instead
            topBar = {
                if (scaffoldState.value.topBarExpandable == ToolbarState.Collapsed) VecoTopBar(
                    navController = navController
                )
            },
            bottomBar = {
                AnimatedVisibility(
                    visible = scaffoldState.value.navigationVisible,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it }),
                    content = {
                        VecoBottomNavigation(navigationProvider)
                    }
                )
            },
            content = {
                if (scaffoldState.value.topBarExpandable == ToolbarState.Expandable)
                    ExpandableToolbarScaffold(
                        modifier = Modifier.padding(it),
                        scaffoldState.value,
                        content = navGraph
                    )
                else navGraph()
            }
        )
    }
}