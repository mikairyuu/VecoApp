package com.veco.vecoapp.android.ui.screen.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.ui.component.MainScaffold
import com.veco.vecoapp.android.ui.component.input.VecoTextField
import com.veco.vecoapp.android.ui.component.misc.VecoButton
import com.veco.vecoapp.android.ui.enums.ToolbarState
import com.veco.vecoapp.android.ui.navigation.AccountScreen
import com.veco.vecoapp.android.ui.theme.regBody1
import com.veco.vecoapp.android.ui.theme.spacing

enum class PasswordChangeStep {
    OLD_PASSWORD,
    NEW_PASSWORD
}

@Composable
fun AccountPassword(navController: NavHostController) {
    MainScaffold(
        stringResource(AccountScreen.ChangePassword.titleId),
        false,
        ToolbarState.Collapsed,
        navController
    ) {
        AccountPasswordContents()
    }
}

@Composable
fun AccountPasswordContents() {
    var step by remember { mutableStateOf(PasswordChangeStep.OLD_PASSWORD) }
    val oldPwdFieldValue = remember { mutableStateOf(TextFieldValue()) }
    val newPwdFieldValue = remember { mutableStateOf(TextFieldValue()) }
    Column(modifier = Modifier.padding(MaterialTheme.spacing.medium, 0.dp)) {
        Column(
            modifier = Modifier.padding(0.dp, 24.dp),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.mini)
        ) {
            Text(
                text = if (step == PasswordChangeStep.OLD_PASSWORD) {
                    stringResource(MR.strings.account_pwd_title_1.resourceId)
                } else {
                    stringResource(MR.strings.account_pwd_title_2.resourceId)
                },
                style = MaterialTheme.typography.h1
            )
            Text(
                text = if (step == PasswordChangeStep.OLD_PASSWORD) {
                    stringResource(MR.strings.account_pwd_desc_1.resourceId)
                } else {
                    stringResource(MR.strings.account_pwd_desc_2.resourceId)
                },
                style = MaterialTheme.typography.regBody1
            )
        }
        VecoTextField(
            textFieldValue = oldPwdFieldValue,
            hint = stringResource(MR.strings.string_password.resourceId)
        )
        Spacer(modifier = Modifier.height(12.dp))
        if (step == PasswordChangeStep.NEW_PASSWORD) {
            VecoTextField(
                textFieldValue = newPwdFieldValue,
                hint = stringResource(MR.strings.string_password_conf.resourceId)
            )
        }
        Box(modifier = Modifier.fillMaxSize()) {
            VecoButton(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.medium)
                    .align(Alignment.BottomCenter),
                text = stringResource(
                    id = if (step == PasswordChangeStep.OLD_PASSWORD) {
                        MR.strings.button_continue.resourceId
                    } else {
                        MR.strings.button_save.resourceId
                    }
                ),
                onClick = {
                    if (step == PasswordChangeStep.OLD_PASSWORD) {
                        step = PasswordChangeStep.NEW_PASSWORD
                    }
                }
            )
        }
    }
}
