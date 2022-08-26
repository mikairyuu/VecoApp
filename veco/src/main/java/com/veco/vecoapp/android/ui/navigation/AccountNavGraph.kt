package com.veco.vecoapp.android.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.ui.SheetSettings
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

fun NavGraphBuilder.accountNavGraph(
    navController: NavHostController,
    bottomSheetState: MutableState<SheetSettings>,
    coroutineScope: CoroutineScope
) {
    navigation(
        startDestination = AccountScreen.Home.route,
        route = Screen.Account.route
    ) {
        composable(AccountScreen.Home.route) {
            AccountHome(navController)
        }
        composable(AccountScreen.PersonalData.route) {
            AccountData(navController)
        }
        composable(AccountScreen.Notifications.route) {
            AccountNotifications(navController)
        }
        composable(AccountScreen.ChangePassword.route) {
            AccountPassword(navController)
        }
        composable(AccountScreen.Prizes.route) {
            AccountPrizes(bottomSheetState, coroutineScope, navController)
        }
    }
}
