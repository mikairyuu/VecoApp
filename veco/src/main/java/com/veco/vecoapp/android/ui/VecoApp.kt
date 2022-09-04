@file:OptIn(ExperimentalMaterialApi::class)

package com.veco.vecoapp.android.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.veco.vecoapp.android.ui.component.MainScaffold
import com.veco.vecoapp.android.ui.component.ScaffoldState
import com.veco.vecoapp.android.ui.component.SheetSettings
import com.veco.vecoapp.android.ui.enums.ToolbarState
import com.veco.vecoapp.android.ui.navigation.ScreenChangeEffect
import com.veco.vecoapp.android.ui.navigation.VecoNavGraph
import com.veco.vecoapp.android.ui.theme.VecoAppTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun VecoApp(startDestination: String) {
    VecoAppTheme {
        val navController = rememberAnimatedNavController()
        val systemUiController = rememberSystemUiController()
        val modalBottomSheetState =
            rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden,
                skipHalfExpanded = true
            )
        val sheetSettings = remember { mutableStateOf(SheetSettings(modalBottomSheetState)) }
        val globalCoroutineScope = rememberCoroutineScope()
        systemUiController.setSystemBarsColor(
            color = Color.White
        )
        val scaffoldState =
            remember { mutableStateOf(ScaffoldState("", false, ToolbarState.Expandable)) }
        val context = LocalContext.current
        ScreenChangeEffect(navController, context, scaffoldState)
        MainScaffold(
            scaffoldState = scaffoldState.value,
            bottomSheetState = sheetSettings.value,
            navController = navController
        ) {
            VecoNavGraph(
                navController = navController,
                sheetSettings = sheetSettings,
                coroutineScope = globalCoroutineScope,
                scaffoldState = scaffoldState,
                startDestination = startDestination
            )
        }
    }
}
