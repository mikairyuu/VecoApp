package com.veco.vecoapp.android.ui.screen.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.ui.component.template.FieldButtonTemplate
import com.veco.vecoapp.android.ui.navigation.AuthScreen
import com.veco.vecoapp.presentation.auth.AuthPwdCodeViewModel

@Composable
fun AuthPasswordCode(
    navController: NavHostController,
    viewModel: AuthPwdCodeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    FieldButtonTemplate(
        uiState = uiState,
        textState = viewModel.code,
        onClick = {
            viewModel.proceed()
        },
        onProceed = { navController.navigate(AuthScreen.PasswordInput.route) },
        title = stringResource(id = MR.strings.auth_pwd_title2.resourceId),
        desc = stringResource(id = MR.strings.auth_pwd_desc2.resourceId),
        buttonText = stringResource(id = MR.strings.auth_pwd_update.resourceId),
        hint = stringResource(id = MR.strings.string_password.resourceId)
    )
}
