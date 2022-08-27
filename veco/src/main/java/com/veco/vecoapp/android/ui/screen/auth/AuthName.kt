package com.veco.vecoapp.android.ui.screen.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.ui.component.MainScaffold
import com.veco.vecoapp.android.ui.component.input.VecoTextField
import com.veco.vecoapp.android.ui.component.misc.VecoButton
import com.veco.vecoapp.android.ui.component.misc.VecoHeadline
import com.veco.vecoapp.android.ui.enums.ToolbarState
import com.veco.vecoapp.android.ui.navigation.Screen
import com.veco.vecoapp.android.ui.theme.spacing
import com.veco.vecoapp.presentation.UIState
import com.veco.vecoapp.presentation.auth.AuthNameViewModel

@Composable
fun AuthName(navController: NavHostController, viewModel: AuthNameViewModel = viewModel()) {
    MainScaffold(
        screenTitle = "",
        navigationVisible = false,
        toolbarState = ToolbarState.Collapsed,
        navController = navController
    ) {
        val uiState by viewModel.uiState.collectAsState()
        val context = LocalContext.current
        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.medium, MaterialTheme.spacing.big)
        ) {
            VecoHeadline(
                text1 = stringResource(id = MR.strings.auth_register_title2.resourceId),
                text2 = stringResource(id = MR.strings.auth_register_desc2.resourceId)
            )
            Spacer(modifier = Modifier.padding(MaterialTheme.spacing.big))
            VecoTextField(
                onValueChange = { true },
                hint = stringResource(id = MR.strings.string_name.resourceId)
            )
            Spacer(modifier = Modifier.padding(12.dp))
            VecoButton(
                text = stringResource(id = MR.strings.auth_register_button.resourceId),
                onClick = {
                    viewModel.proceed(
                        onProceed = { navController.navigate(Screen.Home.route) },
                        onError = { Toast.makeText(context, it, Toast.LENGTH_LONG).show() }
                    )
                },
                isLoading = uiState is UIState.Loading
            )
        }
    }
}
