package com.veco.vecoapp.android.ui.component.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.veco.vecoapp.android.R
import com.veco.vecoapp.android.ui.utils.defaultGradient

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
                    .clip(RoundedCornerShape(16.dp))
                    .defaultGradient()
                    .padding(4.dp, 4.dp, 8.dp, 4.dp)
            ) {
                Row(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Image(
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