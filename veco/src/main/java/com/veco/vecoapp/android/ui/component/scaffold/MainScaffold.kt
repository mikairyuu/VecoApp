package com.veco.vecoapp.android.ui.component.scaffold

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.veco.vecoapp.android.ui.component.BottomSheetScaffold
import com.veco.vecoapp.android.ui.component.ExpandableToolbarScaffold
import com.veco.vecoapp.android.ui.component.SheetSettings
import com.veco.vecoapp.android.ui.enums.ToolbarState
import com.veco.vecoapp.android.ui.navigation.VecoBottomNavigation

data class ScaffoldState(
    val screenTitle: String,
    val navigationVisible: Boolean,
    val toolbarState: ToolbarState
)

@Composable
fun MainScaffold(
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    bottomSheetState: SheetSettings? = null,
    content: @Composable () -> Unit
) {
    BottomSheetScaffold(bottomSheetState) {
        Scaffold(
            bottomBar = {
                if (scaffoldState.navigationVisible) {
                    VecoBottomNavigation(navController = navController)
                }
            },
            content = {
                ExpandableToolbarScaffold(
                    modifier = Modifier.padding(it),
                    screenTitle = scaffoldState.screenTitle,
                    scaffoldState = scaffoldState,
                    navController = navController,
                    content = content
                )
            }
        )
    }
}
