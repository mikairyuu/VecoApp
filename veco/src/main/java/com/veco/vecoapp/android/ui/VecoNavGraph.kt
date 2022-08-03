package com.veco.vecoapp.android.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.veco.vecoapp.android.ui.home.HomeRoute

object VecoDestinations {
    const val HOME_ROUTE = "home"
}

@Composable
fun VecoNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = VecoDestinations.HOME_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(VecoDestinations.HOME_ROUTE) {
            HomeRoute()
        }
    }
}