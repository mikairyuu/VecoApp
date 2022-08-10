package com.veco.vecoapp.android.ui.component.misc

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.veco.vecoapp.android.R
import com.veco.vecoapp.android.ui.theme.regBody2
import com.veco.vecoapp.android.ui.theme.regBody3
import com.veco.vecoapp.android.ui.theme.secondaryText

@Composable
fun DoubleInfoUnit(
    first: String,
    second: String,
    isSmall: Boolean = true,
    needCoin: Boolean = false
) {
    Column {
        Text(
            text = first,
            color = MaterialTheme.colors.secondaryText,
            style = if (isSmall) MaterialTheme.typography.regBody3 else MaterialTheme.typography.regBody2
        )
        Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
            Text(
                text = second,
                style = if (isSmall) MaterialTheme.typography.body2 else MaterialTheme.typography.body1
            )
            if (needCoin) {
                Icon(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    painter = painterResource(R.drawable.ic_veco_pts_clr),
                    tint = Color.Unspecified,
                    contentDescription = null
                )
            }
        }
    }
}