package com.veco.vecoapp.android.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.veco.vecoapp.android.ui.screen.auth.AuthEmail
import com.veco.vecoapp.android.ui.screen.auth.AuthHome
import com.veco.vecoapp.android.ui.screen.auth.AuthName
import com.veco.vecoapp.android.ui.screen.auth.AuthPasswordCode
import com.veco.vecoapp.android.ui.screen.auth.AuthPasswordEmail
import com.veco.vecoapp.android.ui.screen.auth.AuthPasswordInput

sealed class AuthScreen(val route: String) {
    object Home : AuthScreen("auth_home")
    object RegisterEmail : AuthScreen("auth_email")
    object RegisterName : AuthScreen("auth_name")
    object PasswordEmail : AuthScreen("auth_pwd_email")
    object PasswordCode : AuthScreen("auth_pwd_code")
    object PasswordInput : AuthScreen("auth_pwd_input")
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
        composable(AuthScreen.PasswordEmail.route) {
            AuthPasswordEmail(navController)
        }
        composable(AuthScreen.PasswordCode.route) {
            AuthPasswordCode(navController)
        }
        composable(AuthScreen.PasswordInput.route) {
            AuthPasswordInput(navController)
        }
    }
}
