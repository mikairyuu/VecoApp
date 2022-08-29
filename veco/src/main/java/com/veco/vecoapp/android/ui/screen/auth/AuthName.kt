package com.veco.vecoapp.android.ui.screen.auth

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current
    FieldButtonTemplate(
        uiState = uiState,
        onValueChange = { true },
        onClick = {
            viewModel.proceed(
                onProceed = { navController.navigate(Screen.Home.route) },
                onError = { Toast.makeText(context, it, Toast.LENGTH_LONG).show() }
            )
        },
        title = stringResource(id = MR.strings.auth_register_title2.resourceId),
        desc = stringResource(id = MR.strings.auth_register_desc2.resourceId),
        buttonText = stringResource(id = MR.strings.auth_register_button.resourceId),
        hint = stringResource(id = MR.strings.string_name.resourceId)
    )
}
