package com.veco.vecoapp.android.ui.component.misc

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VecoButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    Box(modifier = modifier.padding(16.dp)) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text)
        }
    }
}