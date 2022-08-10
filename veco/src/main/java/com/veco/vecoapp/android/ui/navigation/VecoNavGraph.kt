package com.veco.vecoapp.android.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.veco.vecoapp.android.R
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.ui.ScaffoldState
import com.veco.vecoapp.android.ui.enums.ToolbarState
import com.veco.vecoapp.android.ui.home.HomeRoute

sealed class Screen(val route: String, @StringRes val titleId: Int, @DrawableRes val icon: Int) {
    object Home : Screen("home", MR.strings.home_title.resourceId, R.drawable.ic_home_tasks)
    object Map : Screen("map", MR.strings.map_title.resourceId, R.drawable.ic_home_map)
    object Material :
        Screen("material", MR.strings.material_title.resourceId, R.drawable.ic_home_materials)

    object Account :
        Screen("account", MR.strings.account_title.resourceId, R.drawable.ic_home_account)
}

val destinationList = listOf(
    Screen.Home, Screen.Map,
    Screen.Material, Screen.Account
)

@Composable
fun VecoNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Home.route,
    scaffoldState: MutableState<ScaffoldState>
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
            HomeRoute()
        }
    }
}