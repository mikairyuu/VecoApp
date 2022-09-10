package com.veco.vecoapp.android.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veco.vecoapp.android.ui.theme.secondaryBackground
import com.veco.vecoapp.presentation.misc.Alert
import dev.icerock.moko.resources.compose.localized
import kotlinx.coroutines.delay

@Composable
fun Alert(alert: Alert, onCompletion: () -> Unit) {
    val visible = remember { MutableTransitionState(false) }
    LaunchedEffect(Unit) {
        visible.targetState = true
        delay(5000)
        visible.targetState = false
    }
    LaunchedEffect(visible) {
        if (!visible.currentState && !visible.targetState) onCompletion()
    }
    AnimatedVisibility(
        visibleState = visible,
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it })
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .width(328.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                visible.targetState = false
                            }
                        )
                    },
                backgroundColor = MaterialTheme.colors.secondaryBackground,
                shape = RoundedCornerShape(8.dp),
                elevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = alert.text.localized(),
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.weight(1f, fill = true)
                    )
                    Text(text = alert.emoji, fontSize = 32.sp)
                }
            }
        }
    }
}
