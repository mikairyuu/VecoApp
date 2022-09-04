package com.veco.vecoapp.android.ui.screen.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.ui.component.misc.VecoButton
import com.veco.vecoapp.android.ui.component.unit.VecoInputUnit
import com.veco.vecoapp.presentation.account.AccountDataViewModel

@Composable
fun AccountData(viewModel: AccountDataViewModel = viewModel()) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        VecoInputUnit(
            title = MR.strings.account_name.resourceId,
            textState = viewModel.name
        )
        VecoInputUnit(
            title = MR.strings.string_email.resourceId,
            textState = viewModel.email
        )
        Box(modifier = Modifier.fillMaxSize()) {
            VecoButton(
                modifier = Modifier.align(Alignment.BottomCenter),
                text = stringResource(id = MR.strings.button_save_changes.resourceId),
                onClick = {}
            )
        }
    }
}
