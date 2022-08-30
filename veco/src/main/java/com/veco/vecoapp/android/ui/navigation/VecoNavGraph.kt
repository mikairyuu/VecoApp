package com.veco.vecoapp.android.ui.navigation

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.R
import com.veco.vecoapp.android.ui.component.ScaffoldState
import com.veco.vecoapp.android.ui.component.SheetSettings
import com.veco.vecoapp.android.ui.enums.ToolbarState
import com.veco.vecoapp.android.ui.screen.HomeRoute
import com.veco.vecoapp.android.ui.screen.map.MapHome
import com.veco.vecoapp.android.ui.screen.materials.MaterialDetails
import com.veco.vecoapp.android.ui.screen.materials.MaterialHome
import com.veco.vecoapp.android.ui.screen.misc.ConfirmationRoute
import com.veco.vecoapp.android.ui.screen.misc.ReviewRoute
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
        Screen("account_home", MR.strings.account_title.resourceId, R.drawable.ic_home_account)

    object Review :
        Screen("review", MR.strings.account_title.resourceId, R.drawable.ic_home_account)
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun VecoNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Home.route,
    sheetSettings: MutableState<SheetSettings>,
    coroutineScope: CoroutineScope,
    scaffoldState: MutableState<ScaffoldState>
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Home.route, enterTransition = {
            if (destinationList.find { screen -> screen.route == initialState.destination.route } != null) {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Right)
            } else {
                fadeIn()
            }
        }, exitTransition = { ExitTransition.None }) {
            HomeRoute(navController, coroutineScope, sheetSettings)
        }
        composable(Screen.Map.route, enterTransition = {
            when (initialState.destination.route) {
                Screen.Home.route -> slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
                Screen.Material.route -> slideIntoContainer(AnimatedContentScope.SlideDirection.Right)
                Screen.Account.route -> slideIntoContainer(AnimatedContentScope.SlideDirection.Right)
                else -> fadeIn()
            }
        }, exitTransition = { ExitTransition.None }) {
            MapHome()
        }
        composable(Screen.Material.route, enterTransition = {
            when (initialState.destination.route) {
                Screen.Home.route -> slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
                Screen.Map.route -> slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
                Screen.Account.route -> slideIntoContainer(AnimatedContentScope.SlideDirection.Right)
                else -> fadeIn()
            }
        }, exitTransition = { ExitTransition.None }) {
            MaterialHome(navController)
        }
        composable(
            Screen.MaterialDetails.route + "/{matId}",
            arguments = listOf(navArgument("matId") { type = NavType.IntType })
        ) {
            MaterialDetails(it.arguments?.getInt("matId") ?: 0)
        }
        composable(Screen.Confirmation.route) {
            ConfirmationRoute(navController)
        }
        composable(Screen.Review.route) {
            ReviewRoute(navController)
        }
        accountNavGraph(
            navController,
            sheetSettings,
            coroutineScope
        )
        authNavGraph(
            navController,
            scaffoldState
        )
    }
}

fun vecoScaffoldGraph(
    backStackEntry: NavBackStackEntry,
    context: Context
): ScaffoldState? {
    return when (backStackEntry.destination.route) {
        Screen.Home.route -> {
            ScaffoldState(
                context.getString(Screen.Home.titleId),
                true,
                ToolbarState.ExpandableExpanded
            )
        }
        Screen.Material.route -> {
            ScaffoldState(
                context.getString(Screen.Material.titleId),
                true,
                ToolbarState.Expandable
            )
        }
        Screen.MaterialDetails.route -> {
            ScaffoldState(
                "",
                false,
                ToolbarState.Collapsed
            )
        }
        Screen.Confirmation.route -> {
            ScaffoldState(
                "",
                false,
                ToolbarState.Collapsed
            )
        }
        Screen.Review.route -> {
            ScaffoldState(
                "",
                false,
                ToolbarState.None
            )
        }
        else -> {
            null
        }
    }
}
