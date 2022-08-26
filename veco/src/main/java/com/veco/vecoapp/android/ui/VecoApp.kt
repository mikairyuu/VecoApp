@file:OptIn(ExperimentalMaterialApi::class)

package com.veco.vecoapp.android.ui

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.veco.vecoapp.android.ui.navigation.VecoNavGraph
import com.veco.vecoapp.android.ui.theme.VecoAppTheme

data class SheetSettings(
    val state: ModalBottomSheetState,
    val title: String = "",
    val desc: String = "",
    val points: Int = 0,
    val deadline: String = "",
    val frequency: String = "",
    val buttonText: String = "",
    val color: Color? = null, // Change to bitmap later
    val onClick: () -> Unit = {}
)

@Composable
fun VecoApp() {
    VecoAppTheme {
        val navController = rememberNavController()
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
        VecoNavGraph(
            navController = navController,
            sheetSettings = sheetSettings,
            coroutineScope = globalCoroutineScope
        )
    }
}
