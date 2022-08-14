@file:OptIn(ExperimentalMaterialApi::class)

package com.veco.vecoapp.android.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.R
import com.veco.vecoapp.android.ui.BottomSheetState
import com.veco.vecoapp.android.ui.ScaffoldState
import com.veco.vecoapp.android.ui.enums.ToolbarState
import com.veco.vecoapp.android.ui.screen.account.accountNavGraph
import com.veco.vecoapp.android.ui.screen.confimation.ConfirmationRoute
import com.veco.vecoapp.android.ui.screen.home.HomeRoute
import com.veco.vecoapp.android.ui.screen.materials.MaterialDetails
import com.veco.vecoapp.android.ui.screen.materials.MaterialHome
import kotlinx.coroutines.CoroutineScope

sealed class Screen(
    val route: String,
    @StringRes val titleId: Int,
    @DrawableRes val icon: Int = 0
) {
    object Home : Screen("home", MR.strings.home_title.resourceId, R.drawable.ic_home_tasks)
    object Map : Screen("map", MR.strings.map_title.resourceId, R.drawable.ic_home_map)
    object Material :
        Screen("material", MR.strings.material_title.resourceId, R.drawable.ic_home_materials)

    object MaterialDetails :
        Screen("material_details", MR.strings.material_title.resourceId)

    object Confirmation :
        Screen("confirmation", MR.strings.home_title.resourceId)

    object Account :
        Screen("account", MR.strings.account_title.resourceId, R.drawable.ic_home_account)
}

val destinationList = listOf(
    Screen.Home,
    Screen.Map,
    Screen.Material,
    Screen.Account
)

@Composable
fun VecoNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Home.route,
    scaffoldState: MutableState<ScaffoldState>,
    bottomSheetState: MutableState<BottomSheetState>,
    coroutineScope: CoroutineScope
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            scaffoldState.value = ScaffoldState(
                stringResource(Screen.Home.titleId),
                true,
                ToolbarState.Expandable
            )
            HomeRoute(bottomSheetState, navController, coroutineScope)
        }
        composable(Screen.Confirmation.route) {
            scaffoldState.value = ScaffoldState(
                "",
                false,
                ToolbarState.Collapsed
            )
            ConfirmationRoute()
        }
        composable(Screen.Material.route) {
            scaffoldState.value = ScaffoldState(
                stringResource(Screen.Material.titleId),
                true,
                ToolbarState.Expandable
            )
            MaterialHome(navController)
        }
        composable(
            Screen.MaterialDetails.route + "/{matId}",
            arguments = listOf(navArgument("matId") { type = NavType.IntType })
        ) {
            scaffoldState.value = ScaffoldState(
                "",
                false,
                ToolbarState.Collapsed
            )
            MaterialDetails(it.arguments?.getInt("matId") ?: 0)
        }

        accountNavGraph(
            navController,
            scaffoldState
        )
    }
}
