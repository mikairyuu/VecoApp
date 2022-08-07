package com.veco.vecoapp.android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.veco.vecoapp.MR
import dev.icerock.moko.graphics.colorInt

@Composable
fun VecoTopAppBar() {
    TopAppBar(backgroundColor = Color.White) {
        Row(horizontalArrangement = Arrangement.End) {
            Box(
                modifier = Modifier
                    .size(67.dp, 32.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(MR.colors.gradient_primary_1.color.colorInt()),
                                Color(MR.colors.gradient_primary_2.color.colorInt()),
                                Color(MR.colors.gradient_primary_3.color.colorInt())
                            )
                        )
                    )
            ) {
                Text(
                    150.toString(),
                    style = MaterialTheme.typography.body1,
                    color = Color.White
                )
            }
        }
    }
}