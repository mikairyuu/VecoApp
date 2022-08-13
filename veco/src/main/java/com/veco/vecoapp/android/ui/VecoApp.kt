@file:OptIn(ExperimentalMaterialApi::class)

package com.veco.vecoapp.android.ui

import android.os.Parcelable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.veco.vecoapp.android.ui.component.BottomSheetScaffold
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
    var topBarExpandable: ToolbarState
) : Parcelable

data class BottomSheetState(
    val state: ModalBottomSheetState,
    val title: String = "",
    val desc: String = "",
    val points: Int = 0,
    val deadline: String = "",
    val frequency: String = "",
    val buttonText: String = "",
    val onClick: () -> Unit = {}
)

@Composable
fun VecoApp() {
    VecoAppTheme {
        val navController = rememberNavController()
        val navigationProvider = VecoNavigationProvider(navController)
        val systemUiController = rememberSystemUiController()
        val modalBottomSheetState =
            rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden,
                skipHalfExpanded = true
            )
        val bottomSheetState = remember { mutableStateOf(BottomSheetState(modalBottomSheetState)) }
        val globalCoroutineScope = rememberCoroutineScope()
        systemUiController.setSystemBarsColor(
            color = Color.White
        )
        val scaffoldState = rememberSaveable {
            mutableStateOf(ScaffoldState("", true, ToolbarState.None))
        }
        val navGraph: @Composable () -> Unit = {
            VecoNavGraph(
                navController = navController,
                scaffoldState = scaffoldState,
                bottomSheetState = bottomSheetState,
                coroutineScope = globalCoroutineScope
            )
        }
        BottomSheetScaffold(bottomSheetState.value) {
            Scaffold(
                // If topbar is expandable, we handle it in expandable scaffold instead
                topBar = {
                    if (scaffoldState.value.topBarExpandable == ToolbarState.Collapsed) VecoTopBar(
                        navController = navController,
                        title = scaffoldState.value.screenTitle
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
                    if (scaffoldState.value.topBarExpandable == ToolbarState.Expandable) {
                        ExpandableToolbarScaffold(
                            modifier = Modifier.padding(it),
                            scaffoldState.value,
                            content = navGraph
                        )
                    } else navGraph()
                }
            )
        }
    }
}
