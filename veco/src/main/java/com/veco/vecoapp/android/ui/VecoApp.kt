package com.veco.vecoapp.android.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.veco.vecoapp.android.ui.navigation.VecoBottomNavigation
import com.veco.vecoapp.android.ui.navigation.VecoNavGraph
import com.veco.vecoapp.android.ui.theme.VecoAppTheme

@Composable
fun VecoApp() {
    VecoAppTheme {
        val navController = rememberNavController()
        Scaffold(
            topBar = {
                VecoTopAppBar()
            },
            bottomBar = {
                VecoBottomNavigation()
            },
            content = {
                VecoNavGraph(
                    modifier = Modifier.padding(it),
                    navController = navController,
                )
            }
        )
    }
}