package com.veco.vecoapp.android.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import com.veco.vecoapp.android.ui.component.ScaffoldState

val scaffoldGraphs = listOf(::vecoScaffoldGraph, ::authScaffoldGraph, ::accountScaffoldGraph)

@Composable
fun ScreenChangeEffect(
    navController: NavHostController,
    context: Context,
    scaffoldState: MutableState<ScaffoldState>
) {
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            scaffoldGraphs.forEach loop@{ func ->
                func.invoke(backStackEntry, context)?.let { scaffoldState.value = it; return@loop }
            }
        }
    }
}
