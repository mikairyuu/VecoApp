package com.veco.vecoapp.android.ui.screen.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.ui.component.template.FieldButtonTemplate
import com.veco.vecoapp.presentation.auth.AuthPwdInputViewModel

@Composable
fun AuthPasswordInput(
    navController: NavHostController,
    viewModel: AuthPwdInputViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    FieldButtonTemplate(
        uiState = uiState,
        onClick = {
            viewModel.proceed()
        },
        onProceed = { repeat(3) { navController.popBackStack() } },
        title = stringResource(id = MR.strings.auth_pwd_title3.resourceId),
        desc = stringResource(id = MR.strings.auth_pwd_desc3.resourceId),
        buttonText = stringResource(id = MR.strings.button_continue.resourceId),
        hint = stringResource(id = MR.strings.string_email.resourceId),
        hint2 = stringResource(id = MR.strings.string_password_conf.resourceId),
        textState = viewModel.password,
        textState2 = viewModel.passwordConf
    )
}
