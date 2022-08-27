package com.veco.vecoapp.android.ui.screen.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.ui.component.MainScaffold
import com.veco.vecoapp.android.ui.component.misc.VecoButton
import com.veco.vecoapp.android.ui.component.unit.VecoInputUnit
import com.veco.vecoapp.android.ui.enums.ToolbarState
import com.veco.vecoapp.android.ui.navigation.AccountScreen

@Composable
fun AccountData(navController: NavHostController) {
    MainScaffold(
        stringResource(AccountScreen.PersonalData.titleId),
        false,
        ToolbarState.Collapsed,
        navController = navController
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            VecoInputUnit(title = MR.strings.account_name.resourceId)
            VecoInputUnit(title = MR.strings.string_email.resourceId)
            Box(modifier = Modifier.fillMaxSize()) {
                VecoButton(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    text = stringResource(id = MR.strings.button_save_changes.resourceId),
                    onClick = {}
                )
            }
        }
    }
}
