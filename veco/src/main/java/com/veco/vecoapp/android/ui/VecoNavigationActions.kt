package com.veco.vecoapp.android.ui

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

class VecoNavigationActions(navController: NavHostController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(VecoDestinations.HOME_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}