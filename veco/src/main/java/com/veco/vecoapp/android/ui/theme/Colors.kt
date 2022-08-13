package com.veco.vecoapp.android.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.lightColors
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
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

val Colors.secondaryText: Color
    @Composable
    get() = Color(MR.colors.text_secondary.color.colorInt())

val Colors.tertiaryText: Color
    @Composable
    get() = Color(MR.colors.text_tertiary.color.colorInt())

val Colors.secondaryBackground: Color
    @Composable
    get() = Color(MR.colors.background_secondary.color.colorInt())

val Colors.violet: Color
    @Composable
    get() = Color(MR.colors.violet.color.colorInt())

val Colors.purple: Color
    @Composable
    get() = Color(MR.colors.purple.color.colorInt())

val Colors.orange: Color
    @Composable
    get() = Color(MR.colors.orange.color.colorInt())

val Colors.red: Color
    @Composable
    get() = Color(MR.colors.red.color.colorInt())

val Colors.switchColors: SwitchColors
    @Composable
    get() = SwitchDefaults.colors(
        uncheckedThumbColor = tertiaryText,
        uncheckedTrackColor = secondaryBackground,
        uncheckedBorderColor = tertiaryText,
        checkedTrackColor = primary
    )
