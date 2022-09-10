package com.veco.vecoapp.android.ui.component.scaffold

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.veco.vecoapp.android.ui.component.Alert
import com.veco.vecoapp.android.ui.component.BottomSheetScaffold
import com.veco.vecoapp.android.ui.component.SheetSettings
import com.veco.vecoapp.android.ui.enums.ToolbarState
import com.veco.vecoapp.android.ui.navigation.VecoBottomNavigation
import com.veco.vecoapp.data.PersistentDataManager
import com.veco.vecoapp.presentation.misc.Alert

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
    val alerts = remember { mutableStateListOf<Alert>() }
    var finishedAlerts by remember { mutableStateOf(0) }
    LaunchedEffect(PersistentDataManager.alert) {
        PersistentDataManager.alert.collect {
            if (it != null) {
                alerts.add(it)
            }
        }
    }
    LaunchedEffect(finishedAlerts) {
        if (finishedAlerts == alerts.size) {
            alerts.clear()
            finishedAlerts = 0
        }
    }
    alerts.forEach {
        Alert(it) { finishedAlerts++ }
    }
}
