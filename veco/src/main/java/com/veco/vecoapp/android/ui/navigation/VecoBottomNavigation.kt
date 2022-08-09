package com.veco.vecoapp.android.ui.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.ui.theme.lightGray
import dev.icerock.moko.graphics.colorInt

@Composable
fun VecoBottomNavigation(navProvider: VecoNavigationProvider) {
    BottomNavigation(backgroundColor = Color.White) {
        val navBackStackEntry by navProvider.navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        destinationList.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(painterResource(screen.icon), contentDescription = null) },
                label = {
                    Text(
                        stringResource(screen.titleId),
                        style = MaterialTheme.typography.caption
                    )
                },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                unselectedContentColor = MaterialTheme.colors.lightGray,
                selectedContentColor = MaterialTheme.colors.primary,
                onClick = navProvider.navigateToHome
            )
        }
    }
}