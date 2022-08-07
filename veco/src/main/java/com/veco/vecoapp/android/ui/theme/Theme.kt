package com.veco.vecoapp.android.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun VecoAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = VecoTypography,
        content = content
    )
}