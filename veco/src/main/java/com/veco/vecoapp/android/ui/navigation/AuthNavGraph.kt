package com.veco.vecoapp.android.ui.navigation

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.ui.component.ScaffoldState
import com.veco.vecoapp.android.ui.enums.ToolbarState
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
    navController: NavHostController,
    scaffoldState: MutableState<ScaffoldState>
) {
    navigation(
        startDestination = AuthScreen.Home.route,
        route = "auth"
    ) {
        composable(AuthScreen.Home.route) {
            AuthHome(navController, scaffoldState)
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

fun authScaffoldGraph(
    backStackEntry: NavBackStackEntry,
    context: Context
): ScaffoldState? {
    return when (backStackEntry.destination.route) {
        AuthScreen.Home.route -> {
            // handled by the screen
            null
        }
        AuthScreen.RegisterEmail.route -> {
            ScaffoldState(
                screenTitle = "",
                navigationVisible = false,
                toolbarState = ToolbarState.Collapsed
            )
        }
        AuthScreen.RegisterName.route -> {
            ScaffoldState(
                screenTitle = "",
                navigationVisible = false,
                toolbarState = ToolbarState.Collapsed
            )
        }
        AuthScreen.PasswordEmail.route -> {
            ScaffoldState(
                screenTitle = context.getString(MR.strings.auth_pwd_restore.resourceId),
                navigationVisible = false,
                toolbarState = ToolbarState.Collapsed
            )
        }
        AuthScreen.PasswordCode.route -> {
            ScaffoldState(
                screenTitle = context.getString(MR.strings.auth_pwd_restore.resourceId),
                navigationVisible = false,
                toolbarState = ToolbarState.Collapsed
            )
        }
        AuthScreen.PasswordInput.route -> {
            ScaffoldState(
                screenTitle = context.getString(MR.strings.auth_pwd_restore.resourceId),
                navigationVisible = false,
                toolbarState = ToolbarState.Collapsed
            )
        }
        else -> {
            null
        }
    }
}
