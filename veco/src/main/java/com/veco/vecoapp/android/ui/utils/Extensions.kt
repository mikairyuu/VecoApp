package com.veco.vecoapp.android.ui.utils

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.veco.vecoapp.MR
import dev.icerock.moko.graphics.colorInt

fun Modifier.defaultGradient() = this.background(
    brush = Brush.horizontalGradient(
        colors = listOf(
            Color(MR.colors.gradient_primary_1.color.colorInt()),
            Color(MR.colors.gradient_primary_2.color.colorInt()),
            Color(MR.colors.gradient_primary_3.color.colorInt())
        )
    )
)