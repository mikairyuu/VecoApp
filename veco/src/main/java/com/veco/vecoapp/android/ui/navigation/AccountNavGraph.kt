package com.veco.vecoapp.android.ui.navigation

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.MutableState
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.ui.component.ScaffoldState
import com.veco.vecoapp.android.ui.component.SheetSettings
import com.veco.vecoapp.android.ui.enums.ToolbarState
import com.veco.vecoapp.android.ui.screen.account.AccountData
import com.veco.vecoapp.android.ui.screen.account.AccountHome
import com.veco.vecoapp.android.ui.screen.account.AccountNotifications
import com.veco.vecoapp.android.ui.screen.account.AccountPassword
import com.veco.vecoapp.android.ui.screen.account.AccountPrizes
import kotlinx.coroutines.CoroutineScope

sealed class AccountScreen(val route: String, @StringRes val titleId: Int) {
    object Home : AccountScreen("account_home", MR.strings.account_title.resourceId)
    object PersonalData : AccountScreen("account_data", MR.strings.account_data.resourceId)
    object Notifications :
        AccountScreen("notifications", MR.strings.account_notifications.resourceId)

    object ChangePassword :
        AccountScreen("change_password", MR.strings.account_change_pwd.resourceId)

    object Prizes :
        AccountScreen("account_prizes", MR.strings.account_prizes.resourceId)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.accountNavGraph(
    navController: NavHostController,
    bottomSheetState: MutableState<SheetSettings>,
    coroutineScope: CoroutineScope
) {
    navigation(
        startDestination = AccountScreen.Home.route,
        route = "account"
    ) {
        composable(AccountScreen.Home.route, enterTransition = {
            if (initialState.destination.route == AccountScreen.Home.route) {
                EnterTransition.None
            } else if (destinationList.find { screen -> screen.route == initialState.destination.route } != null) {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
            } else {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Right)
            }
        }, exitTransition = { ExitTransition.None }) {
            AccountHome(navController)
        }
        composable(
            AccountScreen.PersonalData.route,
            enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left) },
            exitTransition = { ExitTransition.None }
        ) {
            AccountData()
        }
        composable(
            AccountScreen.Notifications.route,
            enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left) },
            exitTransition = { ExitTransition.None }
        ) {
            AccountNotifications()
        }
        composable(
            AccountScreen.ChangePassword.route,
            enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left) },
            exitTransition = { ExitTransition.None }
        ) {
            AccountPassword(navController)
        }
        composable(
            AccountScreen.Prizes.route,
            enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left) },
            exitTransition = { ExitTransition.None }
        ) {
            AccountPrizes(bottomSheetState, coroutineScope)
        }
    }
}

fun accountScaffoldGraph(
    backStackEntry: NavBackStackEntry,
    context: Context
): ScaffoldState? {
    return when (backStackEntry.destination.route) {
        AccountScreen.Home.route -> {
            ScaffoldState(
                context.getString(AccountScreen.Home.titleId),
                true,
                ToolbarState.ExpandableExpanded
            )
        }
        AccountScreen.PersonalData.route -> {
            ScaffoldState(
                context.getString(AccountScreen.PersonalData.titleId),
                false,
                ToolbarState.Collapsed
            )
        }
        AccountScreen.Notifications.route -> {
            ScaffoldState(
                context.getString(AccountScreen.Notifications.titleId),
                false,
                ToolbarState.Collapsed
            )
        }
        AccountScreen.ChangePassword.route -> {
            ScaffoldState(
                context.getString(AccountScreen.ChangePassword.titleId),
                false,
                ToolbarState.Collapsed
            )
        }
        AccountScreen.Prizes.route -> {
            ScaffoldState(
                context.getString(AccountScreen.Prizes.titleId),
                false,
                ToolbarState.Collapsed
            )
        }
        else -> {
            null
        }
    }
}
