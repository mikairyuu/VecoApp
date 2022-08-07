package com.veco.vecoapp.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.veco.vecoapp.android.R
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.ui.home.HomeRoute

data class NavDestination(
    val id: String,
    val icon: Int,
    val text: Int
)

object VecoDestinations {
    val HOME_ROUTE = NavDestination(
        "home",
        R.drawable.ic_home_tasks,
        MR.strings.home_title.resourceId
    )
    val MAP_ROUTE = NavDestination(
        "map",
        R.drawable.ic_home_map,
        MR.strings.map_title.resourceId
    )
    val MATERIAL_ROUTE = NavDestination(
        "material",
        R.drawable.ic_home_materials,
        MR.strings.material_title.resourceId
    )
    val ACCOUNT_ROUTE = NavDestination(
        "account",
        R.drawable.ic_home_account,
        MR.strings.account_title.resourceId
    )
}

val destinationList = listOf(
    VecoDestinations.HOME_ROUTE, VecoDestinations.MAP_ROUTE,
    VecoDestinations.MATERIAL_ROUTE, VecoDestinations.ACCOUNT_ROUTE
)

@Composable
fun VecoNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = VecoDestinations.HOME_ROUTE.id
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(VecoDestinations.HOME_ROUTE.id) {
            HomeRoute()
        }
    }
}