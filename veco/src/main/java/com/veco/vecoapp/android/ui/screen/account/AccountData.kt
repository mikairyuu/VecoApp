package com.veco.vecoapp.android.ui.screen.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.ui.component.misc.VecoButton
import com.veco.vecoapp.android.ui.component.unit.VecoInputUnit

@Composable
fun AccountData() {
    val nameField = remember { mutableStateOf(TextFieldValue("")) }
    val emailField = remember { mutableStateOf(TextFieldValue("")) }
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        VecoInputUnit(title = MR.strings.account_name.resourceId, textFieldValue = nameField)
        VecoInputUnit(title = MR.strings.account_email.resourceId, textFieldValue = emailField)
        Box(modifier = Modifier.fillMaxSize()) {
            VecoButton(
                modifier = Modifier.align(Alignment.BottomCenter),
                text = stringResource(id = MR.strings.button_save.resourceId),
                onClick = {}
            )
        }
    }
}
