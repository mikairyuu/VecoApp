package com.veco.vecoapp.android.ui.screen.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.ui.component.template.FieldButtonTemplate
import com.veco.vecoapp.android.ui.navigation.Screen
import com.veco.vecoapp.presentation.auth.AuthNameViewModel

@Composable
fun AuthName(navController: NavHostController, viewModel: AuthNameViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    FieldButtonTemplate(
        uiState = uiState,
        onClick = {
            viewModel.proceed()
        },
        textState = viewModel.name,
        onProceed = { navController.navigate(Screen.Home.route) },
        title = stringResource(id = MR.strings.auth_register_title2.resourceId),
        desc = stringResource(id = MR.strings.auth_register_desc2.resourceId),
        buttonText = stringResource(id = MR.strings.auth_register_button.resourceId),
        hint = stringResource(id = MR.strings.string_name.resourceId)
    )
}
