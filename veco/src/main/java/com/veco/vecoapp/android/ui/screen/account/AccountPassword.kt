package com.veco.vecoapp.android.ui.screen.account

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.ui.component.input.VecoTextField
import com.veco.vecoapp.android.ui.component.misc.VecoButton
import com.veco.vecoapp.android.ui.component.misc.VecoHeadline
import com.veco.vecoapp.android.ui.navigation.AuthScreen
import com.veco.vecoapp.android.ui.theme.spacing
import com.veco.vecoapp.presentation.account.AccountPasswordViewModel
import com.veco.vecoapp.presentation.account.PasswordChangeStep

@Composable
fun AccountPassword(
    navController: NavHostController,
    viewModel: AccountPasswordViewModel = viewModel()
) {
    val step by viewModel.step.collectAsState()
    Column(
        modifier = Modifier.padding(MaterialTheme.spacing.medium, 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        VecoHeadline(
            text1 = if (step == PasswordChangeStep.OLD_PASSWORD) {
                stringResource(MR.strings.account_pwd_title_1.resourceId)
            } else {
                stringResource(MR.strings.account_pwd_title_2.resourceId)
            },
            text2 = if (step == PasswordChangeStep.OLD_PASSWORD) {
                stringResource(MR.strings.account_pwd_desc_1.resourceId)
            } else {
                stringResource(MR.strings.account_pwd_desc_2.resourceId)
            }
        )
        AccountPasswordContents(step, navController, viewModel)
    }
}

@Composable
fun AccountPasswordContents(
    step: PasswordChangeStep,
    navController: NavHostController,
    viewModel: AccountPasswordViewModel
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        VecoTextField(
            hint = stringResource(MR.strings.string_password.resourceId),
            onValueChange = { true }
        )
        if (step == PasswordChangeStep.NEW_PASSWORD) {
            VecoTextField(
                hint = stringResource(MR.strings.string_password_conf.resourceId),
                onValueChange = { true }
            )
        }
        if (step == PasswordChangeStep.OLD_PASSWORD) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate(AuthScreen.PasswordEmail.route) },
                text = stringResource(id = MR.strings.button_forgot_pwd.resourceId),
                style = MaterialTheme.typography.body2
            )
        }
        VecoButton(
            text = stringResource(
                id = if (step == PasswordChangeStep.OLD_PASSWORD) {
                    MR.strings.button_continue.resourceId
                } else {
                    MR.strings.button_save_changes.resourceId
                }
            ),
            onClick = {
                viewModel.changeStep()
            }
        )
    }
}
