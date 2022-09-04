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
import com.veco.vecoapp.presentation.auth.AuthPwdEmailViewModel

@Composable
fun AuthPasswordEmail(navController: NavHostController, viewModel: AuthPwdEmailViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    FieldButtonTemplate(
        uiState = uiState,
        onClick = {
            viewModel.proceed()
        },
        onProceed = { navController.navigate(AuthScreen.PasswordCode.route) },
        title = stringResource(id = MR.strings.auth_pwd_title1.resourceId),
        desc = stringResource(id = MR.strings.auth_pwd_desc1.resourceId),
        buttonText = stringResource(id = MR.strings.button_continue.resourceId),
        hint = stringResource(id = MR.strings.string_email.resourceId),
        textState = viewModel.email
    )
}
