package com.veco.vecoapp.android.ui.screen.account

import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.ui.ScaffoldState
import com.veco.vecoapp.android.ui.enums.ToolbarState
import com.veco.vecoapp.android.ui.navigation.Screen

sealed class AccountScreen(val route: String, @StringRes val titleId: Int) {
    object Home : AccountScreen("account_home", MR.strings.account_title.resourceId)
    object PersonalData : AccountScreen("account_data", MR.strings.account_data.resourceId)
}

fun NavGraphBuilder.accountNavGraph(
    navController: NavController,
    scaffoldState: MutableState<ScaffoldState>
) {
    navigation(
        startDestination = AccountScreen.Home.route,
        route = Screen.Account.route
    ) {
        composable(AccountScreen.Home.route) {
            scaffoldState.value = ScaffoldState(
                stringResource(AccountScreen.Home.titleId),
                true,
                ToolbarState.Expandable
            )
            AccountHome(navController)
        }
        composable(AccountScreen.PersonalData.route) {
            scaffoldState.value = ScaffoldState(
                stringResource(AccountScreen.PersonalData.titleId),
                false,
                ToolbarState.Collapsed
            )
            AccountData()
        }
    }
}