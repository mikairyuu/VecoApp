package com.veco.vecoapp.android.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import com.veco.vecoapp.MR
import dev.icerock.moko.graphics.colorInt

@Composable
fun vecoLightColors() = lightColors(
    primary = Color(MR.colors.brand_default.color.colorInt()),
    onBackground = Color(MR.colors.greyscale_black.color.colorInt())
)

val Colors.lightGray: Color
    @Composable
    get() = Color(MR.colors.greyscale_light_grey.color.colorInt())