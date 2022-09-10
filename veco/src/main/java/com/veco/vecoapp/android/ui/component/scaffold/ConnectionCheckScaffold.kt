package com.veco.vecoapp.android.ui.component.scaffold

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.veco.vecoapp.android.R
import com.veco.vecoapp.android.ui.component.misc.VecoProgressIndicator
import com.veco.vecoapp.android.ui.theme.regBody1
import com.veco.vecoapp.data.PersistentDataManager

@Composable
fun ConnectionCheckScaffold(onConnection: () -> Unit, block: @Composable () -> Unit) {
    val connected by PersistentDataManager.connected.collectAsState()
    LaunchedEffect(connected) {
        if (connected) {
            onConnection()
        }
    }
    if (connected) {
        block()
    } else {
        NoConnectionScreen()
    }
}

@Composable
fun NoConnectionScreen() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(100.dp))
        Image(
            modifier = Modifier.size(156.dp),
            painter = painterResource(id = R.drawable.img_gears),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(id = R.string.error_no_network_title),
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(id = R.string.error_no_network_desc),
            style = MaterialTheme.typography.regBody1,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        VecoProgressIndicator(modifier = Modifier.size(48.dp))
    }
}
