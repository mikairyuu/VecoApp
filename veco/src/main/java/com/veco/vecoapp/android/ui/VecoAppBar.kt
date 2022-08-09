package com.veco.vecoapp.android.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.lerp
import androidx.compose.ui.unit.dp
import com.veco.vecoapp.android.R
import com.veco.vecoapp.android.ui.theme.spacing
import com.veco.vecoapp.android.ui.utils.defaultGradient
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.CollapsingToolbarScope

@Composable
fun VecoTopAppBar(
    toolbarScope: CollapsingToolbarScope,
    scaffoldState: CollapsingToolbarScaffoldState,
    mainTitle: String
) {
    toolbarScope.apply {
        Box(
            modifier = Modifier
                .height(150.dp)
                .pin()
                .background(color = Color.White)
                .fillMaxWidth()
        ) {
            VecoPoints(boxScope = this)
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
                Decoration2(
                    contentColor = MaterialTheme.colors.onBackground,
                    typography = labelAnimatedStyle,
                    content = {
                        Text(
                            modifier = Modifier
                                .road(
                                    whenCollapsed = Alignment.TopStart,
                                    whenExpanded = Alignment.BottomStart
                                )
                                .padding(MaterialTheme.spacing.medium),
                            text = mainTitle
                        )
                    }
                )
            }
        decoratedLabel()
    }
}

@Composable
fun VecoPoints(boxScope: BoxScope) {
    boxScope.apply {
        Box(
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.TopEnd)
        ) {
            Box(
                modifier = Modifier
                    .size(67.dp, 32.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .defaultGradient()
                    .padding(6.dp, 4.dp, 8.dp, 4.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                    Image(
                        modifier = Modifier.padding(2.dp),
                        painter = painterResource(id = R.drawable.ic_veco_pts),
                        contentDescription = null
                    )
                    Text(
                        text = 150.toString(),
                        style = MaterialTheme.typography.body1,
                        color = Color.White,
                    )
                }
            }
        }
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
            size = Size(size.width, 28f),
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