package com.veco.vecoapp.android.ui.screen.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.R
import com.veco.vecoapp.android.ui.component.MainScaffold
import com.veco.vecoapp.android.ui.component.misc.VecoHeadline
import com.veco.vecoapp.android.ui.component.misc.VecoIconButton
import com.veco.vecoapp.android.ui.enums.ToolbarState
import com.veco.vecoapp.android.ui.navigation.AuthScreen
import com.veco.vecoapp.android.ui.theme.secondaryBackground
import com.veco.vecoapp.android.ui.theme.spacing
import com.veco.vecoapp.presentation.auth.AuthEmailViewModel

@Composable
fun AuthEmail(navController: NavHostController, viewModel: AuthEmailViewModel = viewModel()) {
    MainScaffold(
        screenTitle = "",
        navigationVisible = false,
        toolbarState = ToolbarState.Collapsed,
        navController = navController
    ) {
        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.medium, MaterialTheme.spacing.big),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.big)
        ) {
            VecoHeadline(
                text1 = stringResource(id = MR.strings.auth_register_title.resourceId),
                text2 = stringResource(id = MR.strings.auth_register_desc.resourceId)
            )
            VecoIconButton(
                onClick = { navController.navigate(AuthScreen.RegisterName.route) },
                text = stringResource(id = MR.strings.auth_register_send_again.resourceId),
                iconTint = MaterialTheme.colors.onBackground,
                backgroundColor = MaterialTheme.colors.secondaryBackground,
                icon = R.drawable.ic_resend
            )
        }
    }
}
