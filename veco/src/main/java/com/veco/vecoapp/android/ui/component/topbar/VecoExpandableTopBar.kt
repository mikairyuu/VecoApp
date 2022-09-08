package com.veco.vecoapp.android.ui.component.topbar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.lerp
import androidx.compose.ui.unit.dp
import com.veco.vecoapp.android.ui.theme.spacing
import com.veco.vecoapp.data.PersistentDataManager
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.CollapsingToolbarScope

@Composable
fun VecoExpandableTopBar(
    toolbarScope: CollapsingToolbarScope,
    scaffoldState: CollapsingToolbarScaffoldState,
    mainTitle: String
) {
    toolbarScope.apply {
        val userData by PersistentDataManager.userData.collectAsState()
        Box(
            modifier = Modifier
                .height(100.dp)
                .pin()
                .background(color = Color.White)
                .fillMaxWidth()
        ) {
            VecoPoints(boxScope = this, userData?.points)
        }

        // Label animation
        val animRem = animateFloatAsState(scaffoldState.toolbarState.progress)
        val decoratedLabel: @Composable (() -> Unit) =
            @Composable {
                val labelAnimatedStyle = lerp(
                    MaterialTheme.typography.h2,
                    MaterialTheme.typography.h1,
                    animRem.value
                )
                val topPadding = androidx.compose.ui.unit.lerp(
                    14.dp,
                    4.dp,
                    animRem.value
                )
                val bottomPadding = androidx.compose.ui.unit.lerp(
                    14.dp,
                    MaterialTheme.spacing.small,
                    animRem.value
                )
                val horizontalPadding = androidx.compose.ui.unit.lerp(
                    12.dp,
                    MaterialTheme.spacing.medium,
                    animRem.value
                )
                Decoration2(
                    contentColor = MaterialTheme.colors.onBackground,
                    typography = labelAnimatedStyle,
                    content = {
                        Text(
                            modifier = Modifier
                                .road(
                                    whenCollapsed = Alignment.BottomStart,
                                    whenExpanded = Alignment.BottomStart
                                )
                                .padding(
                                    horizontalPadding,
                                    topPadding,
                                    horizontalPadding,
                                    bottomPadding
                                ),
                            text = mainTitle
                        )
                    }
                )
            }
        decoratedLabel()
    }
}

fun topAppBarShadow(drawScope: DrawScope, expandProgress: Float) {
    drawScope.apply {
        val size = size
        val shadowStart =
            Color.Black.copy(alpha = 0.2f * (1 - expandProgress))
        val shadowEnd = Color.Transparent
        drawRect(
            brush = Brush.verticalGradient(
                listOf(shadowStart, shadowEnd),
                startY = size.height,
                endY = size.height + 28f
            ),
            topLeft = Offset(0f, size.height),
            size = Size(size.width, 28f)
        )
    }
}

@Composable
fun Decoration2(
    contentColor: Color,
    typography: TextStyle? = null,
    contentAlpha: Float? = null,
    content: @Composable () -> Unit
) {
    val colorAndEmphasis: @Composable () -> Unit = @Composable {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            if (contentAlpha != null) {
                CompositionLocalProvider(
                    LocalContentAlpha provides contentAlpha,
                    content = content
                )
            } else {
                CompositionLocalProvider(
                    LocalContentAlpha provides contentColor.alpha,
                    content = content
                )
            }
        }
    }
    if (typography != null) ProvideTextStyle(typography, colorAndEmphasis) else colorAndEmphasis()
}
