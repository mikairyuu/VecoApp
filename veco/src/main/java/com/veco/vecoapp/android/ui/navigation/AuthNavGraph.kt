package com.veco.vecoapp.android.ui.navigation

import android.content.Context
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.MutableState
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.ui.component.scaffold.ScaffoldState
import com.veco.vecoapp.android.ui.enums.ToolbarState
import com.veco.vecoapp.android.ui.screen.auth.AuthEmail
import com.veco.vecoapp.android.ui.screen.auth.AuthHome
import com.veco.vecoapp.android.ui.screen.auth.AuthName
import com.veco.vecoapp.android.ui.screen.auth.AuthOnboard
import com.veco.vecoapp.android.ui.screen.auth.AuthPasswordCode
import com.veco.vecoapp.android.ui.screen.auth.AuthPasswordEmail
import com.veco.vecoapp.android.ui.screen.auth.AuthPasswordInput

sealed class AuthScreen(val route: String) {
    object Onboard : AuthScreen("auth_onboard")
    object Home : AuthScreen("auth_home")
    object RegisterEmail : AuthScreen("auth_email")
    object RegisterName : AuthScreen("auth_name")
    object PasswordEmail : AuthScreen("auth_pwd_email")
    object PasswordCode : AuthScreen("auth_pwd_code")
    object PasswordInput : AuthScreen("auth_pwd_input")
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    scaffoldState: MutableState<ScaffoldState>
) {
    navigation(
        startDestination = AuthScreen.Onboard.route,
        route = Screen.Auth.route
    ) {
        composable(AuthScreen.Home.route, enterTransition = {
            if (initialState.destination.route == AuthScreen.Onboard.route) {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left) + fadeIn(animationSpec = tween(500))
            } else {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Right)
            }
        }, exitTransition = { ExitTransition.None }) {
            AuthHome(navController, scaffoldState)
        }
        composable(AuthScreen.RegisterEmail.route, enterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
        }, exitTransition = { ExitTransition.None }) {
            AuthEmail(navController)
        }
        composable(AuthScreen.RegisterName.route, enterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
        }, exitTransition = { ExitTransition.None }) {
            AuthName(navController)
        }
        composable(AuthScreen.PasswordEmail.route, enterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
        }, exitTransition = { ExitTransition.None }) {
            AuthPasswordEmail(navController)
        }
        composable(AuthScreen.PasswordCode.route, enterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
        }, exitTransition = { ExitTransition.None }) {
            AuthPasswordCode(navController)
        }
        composable(AuthScreen.PasswordInput.route, enterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
        }, exitTransition = { ExitTransition.None }) {
            AuthPasswordInput(navController)
        }
        composable(AuthScreen.Onboard.route) {
            AuthOnboard(navController)
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
        AuthScreen.Onboard.route -> {
            ScaffoldState(
                screenTitle = "",
                navigationVisible = false,
                toolbarState = ToolbarState.None
            )
        }
        else -> {
            null
        }
    }
}
