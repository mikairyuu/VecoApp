package com.veco.vecoapp.android.ui.screen.auth

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
import com.veco.vecoapp.android.ui.enums.ToolbarState
import com.veco.vecoapp.android.ui.theme.regBody1
import com.veco.vecoapp.android.ui.theme.regBody3
import com.veco.vecoapp.android.ui.theme.secondaryText
import com.veco.vecoapp.android.ui.theme.tertiaryText
import com.veco.vecoapp.presentation.auth.AuthHomeViewModel
import com.veco.vecoapp.presentation.auth.AuthState

@Composable
fun AuthHome(
    navController: NavHostController,
    viewModel: AuthHomeViewModel = viewModel()
) {
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
            AuthHeadline(authState)
            AuthInputArea(authState)
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
fun AuthHeadline(authState: AuthState) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = stringResource(
                id = if (authState == AuthState.LOGIN) {
                    MR.strings.auth_login_title.resourceId
                } else {
                    MR.strings.string_register.resourceId
                }
            ),
            style = MaterialTheme.typography.h1
        )
        Text(
            text = stringResource(
                id = if (authState == AuthState.LOGIN) {
                    MR.strings.auth_login_desc.resourceId
                } else {
                    MR.strings.auth_register_desc.resourceId
                }
            ),
            style = MaterialTheme.typography.regBody1
        )
    }
}

@Composable
fun AuthInputArea(authState: AuthState) {
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
            onClick = {}
        )
    }
}

@Composable
fun AuthSocialButtons() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IconWhiteButton(
            onClick = { },
            text = stringResource(id = MR.strings.auth_sber.resourceId),
            icon = R.drawable.ic_sber
        )
        IconWhiteButton(
            onClick = { },
            text = stringResource(id = MR.strings.auth_vk.resourceId),
            icon = R.drawable.ic_vk
        )
        IconWhiteButton(
            onClick = { },
            text = stringResource(id = MR.strings.auth_google.resourceId),
            icon = R.drawable.ic_google
        )
    }
}

@Composable
fun IconWhiteButton(
    onClick: () -> Unit,
    text: String,
    @DrawableRes icon: Int
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        border = BorderStroke(1.dp, MaterialTheme.colors.tertiaryText),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick,
        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp, pressedElevation = 4.dp)
    ) {
        Row(horizontalArrangement = Arrangement.Center) {
            Icon(
                painter = painterResource(id = icon),
                tint = Color.Unspecified,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, style = MaterialTheme.typography.body1)
        }
    }
}
