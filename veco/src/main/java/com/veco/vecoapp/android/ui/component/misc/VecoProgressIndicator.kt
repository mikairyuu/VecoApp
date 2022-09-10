package com.veco.vecoapp.android.ui.component.misc

import androidx.compose.material.MaterialTheme
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VecoProgressIndicator(modifier: Modifier = Modifier, progress: Float? = null) {
    if (progress == null) {
        CircularProgressIndicator(
            modifier = modifier,
            color = MaterialTheme.colors.primary,
            strokeWidth = 2.5.dp
        )
    } else {
        CircularProgressIndicator(
            progress = progress,
            modifier = modifier,
            color = MaterialTheme.colors.primary,
            strokeWidth = 2.5.dp
        )
    }
}
