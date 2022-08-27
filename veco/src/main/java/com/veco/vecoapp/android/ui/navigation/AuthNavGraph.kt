package com.veco.vecoapp.android.ui.navigation

import androidx.annotation.StringRes
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.ui.screen.auth.AuthEmail
import com.veco.vecoapp.android.ui.screen.auth.AuthHome
import com.veco.vecoapp.android.ui.screen.auth.AuthName

sealed class AuthScreen(val route: String, @StringRes val titleId: Int) {
    object Home : AuthScreen("auth_home", MR.strings.account_title.resourceId)
    object RegisterEmail : AuthScreen("auth_email", MR.strings.account_title.resourceId)
    object RegisterName : AuthScreen("auth_name", MR.strings.account_title.resourceId)
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
        composable(AuthScreen.RegisterEmail.route) {
            AuthEmail(navController)
        }
        composable(AuthScreen.RegisterName.route) {
            AuthName(navController)
        }
    }
}
