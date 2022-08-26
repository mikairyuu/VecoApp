package com.veco.vecoapp.android.ui.screen.account

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.ui.component.MainScaffold
import com.veco.vecoapp.android.ui.enums.ToolbarState
import com.veco.vecoapp.android.ui.navigation.AccountScreen
import com.veco.vecoapp.android.ui.theme.regBody1
import com.veco.vecoapp.android.ui.theme.spacing
import com.veco.vecoapp.android.ui.theme.switchColors

data class NotificationOption(
    @StringRes val title: Int,
    val onClick: (Boolean) -> Unit
)

val notificationOptions = listOf(
    NotificationOption(MR.strings.notification_title_1.resourceId, {}),
    NotificationOption(MR.strings.notification_title_2.resourceId, {}),
    NotificationOption(MR.strings.notification_title_3.resourceId, {})
)

@Composable
fun AccountNotifications(navController: NavHostController) {
    MainScaffold(
        stringResource(AccountScreen.Notifications.titleId),
        false,
        ToolbarState.Collapsed,
        navController
    ) {
        Column(modifier = Modifier.padding(0.dp, MaterialTheme.spacing.small)) {
            notificationOptions.forEach {
                ToggleUnit(title = it.title, onCheckedChange = it.onClick)
            }
        }
    }
}

@Composable
fun ToggleUnit(@StringRes title: Int, onCheckedChange: (Boolean) -> Unit) {
    var isChecked by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .clickable { isChecked = !isChecked; onCheckedChange(isChecked) }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = title), style = MaterialTheme.typography.regBody1)
        Box(modifier = Modifier.fillMaxWidth()) {
            Switch(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(52.dp, 32.dp),
                checked = isChecked,
                onCheckedChange = { isChecked = it; onCheckedChange(it) },
                colors = MaterialTheme.colors.switchColors
            )
        }
    }
}
