package com.veco.vecoapp.android.ui.navigation

import androidx.annotation.StringRes
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.ui.screen.auth.AuthHome

sealed class AuthScreen(val route: String, @StringRes val titleId: Int) {
    object Home : AuthScreen("auth_home", MR.strings.account_title.resourceId)
}

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = AuthScreen.Home.route,
        route = "auth"
    ) {
        composable(AuthScreen.Home.route) {
            AuthHome(navController)
        }
    }
}
