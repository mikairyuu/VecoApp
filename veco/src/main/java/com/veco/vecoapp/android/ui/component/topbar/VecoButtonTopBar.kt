package com.veco.vecoapp.android.ui.component.topbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun VecoButtonTopBar(title: String, onClick: () -> Unit) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(onClick = onClick)
                    .padding(16.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    text = title,
                    style = MaterialTheme.typography.body1
                )
            }
        },
        backgroundColor = Color.White
    )
}
