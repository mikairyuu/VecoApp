package com.veco.vecoapp.android.ui.screen.auth

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.R
import com.veco.vecoapp.android.ui.component.MainScaffold
import com.veco.vecoapp.android.ui.component.input.VecoTextField
import com.veco.vecoapp.android.ui.component.misc.VecoButton
import com.veco.vecoapp.android.ui.component.misc.VecoHeadline
import com.veco.vecoapp.android.ui.component.misc.VecoIconButton
import com.veco.vecoapp.android.ui.enums.ToolbarState
import com.veco.vecoapp.android.ui.navigation.AuthScreen
import com.veco.vecoapp.android.ui.navigation.Screen
import com.veco.vecoapp.android.ui.theme.regBody3
import com.veco.vecoapp.android.ui.theme.secondaryText
import com.veco.vecoapp.android.ui.theme.tertiaryText
import com.veco.vecoapp.presentation.UIState
import com.veco.vecoapp.presentation.auth.AuthHomeViewModel
import com.veco.vecoapp.presentation.auth.AuthState

@Composable
fun AuthHome(
    navController: NavHostController,
    viewModel: AuthHomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val authState by viewModel.authState.collectAsState()
    MainScaffold(
        stringResource(
            id = if (authState == AuthState.LOGIN) {
                MR.strings.string_register.resourceId
            } else {
                MR.strings.auth_login_button.resourceId
            }
        ),
        false,
        ToolbarState.Button {
            viewModel.changeAuthState()
        },
        navController = navController
    ) {
        Column(
            modifier = Modifier.padding(16.dp, 24.dp, 16.dp, 0.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            VecoHeadline(
                stringResource(
                    id = if (authState == AuthState.LOGIN) {
                        MR.strings.auth_login_title.resourceId
                    } else {
                        MR.strings.string_register.resourceId
                    }
                ),
                stringResource(
                    id = if (authState == AuthState.LOGIN) {
                        MR.strings.auth_login_desc.resourceId
                    } else {
                        MR.strings.auth_register_desc.resourceId
                    }
                )
            )
            AuthInputArea(authState, viewModel, navController, uiState)
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = MR.strings.auth_other_ways.resourceId),
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center
            )
            AuthSocialButtons()
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(
                    id = if (authState == AuthState.LOGIN) {
                        MR.strings.auth_login_policy.resourceId
                    } else {
                        MR.strings.auth_register_policy.resourceId
                    }
                ),
                style = MaterialTheme.typography.regBody3,
                color = MaterialTheme.colors.secondaryText,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AuthInputArea(
    authState: AuthState,
    viewModel: AuthHomeViewModel,
    navController: NavHostController,
    uiState: UIState<Nothing?>
) {
    val context = LocalContext.current
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        VecoTextField(
            hint = stringResource(id = MR.strings.string_email.resourceId),
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email,
            onValueChange = { true }
        )
        VecoTextField(
            hint = stringResource(id = MR.strings.string_password.resourceId),
            imeAction = if (authState == AuthState.LOGIN) ImeAction.Done else ImeAction.Next,
            keyboardType = KeyboardType.Password,
            onValueChange = { true }
        )
        if (authState == AuthState.REGISTER) {
            VecoTextField(
                hint = stringResource(id = MR.strings.string_password_conf.resourceId),
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password,
                onValueChange = { true }
            )
        } else {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { },
                text = stringResource(id = MR.strings.button_forgot_pwd.resourceId),
                style = MaterialTheme.typography.body2
            )
        }
        VecoButton(
            text = stringResource(
                id = if (authState == AuthState.LOGIN) {
                    MR.strings.string_login.resourceId
                } else {
                    MR.strings.button_continue.resourceId
                }
            ),
            isLoading = uiState is UIState.Loading,
            onClick = {
                viewModel.proceed(onError = {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }, onProceed = {
                    if (authState == AuthState.REGISTER) {
                        navController.navigate(
                            AuthScreen.RegisterEmail.route
                        )
                    } else {
                        navController.navigate(
                            Screen.Home.route
                        ) {
                            popUpTo(0)
                            launchSingleTop
                        }
                    }
                })
            }
        )
    }
}

@Composable
fun AuthSocialButtons() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AuthSocialButton(
            onClick = { },
            text = stringResource(id = MR.strings.auth_sber.resourceId),
            icon = R.drawable.ic_sber
        )
        AuthSocialButton(
            onClick = { },
            text = stringResource(id = MR.strings.auth_vk.resourceId),
            icon = R.drawable.ic_vk
        )
        AuthSocialButton(
            onClick = { },
            text = stringResource(id = MR.strings.auth_google.resourceId),
            icon = R.drawable.ic_google
        )
    }
}

@Composable
fun AuthSocialButton(onClick: () -> Unit, text: String, @DrawableRes icon: Int) {
    VecoIconButton(
        onClick = onClick,
        text = text,
        icon = icon,
        backgroundColor = Color.White,
        borderStroke = BorderStroke(1.dp, MaterialTheme.colors.tertiaryText)
    )
}
