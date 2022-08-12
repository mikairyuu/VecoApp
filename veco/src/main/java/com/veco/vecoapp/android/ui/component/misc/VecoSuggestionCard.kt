package com.veco.vecoapp.android.ui.component.misc

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.veco.vecoapp.android.R
import com.veco.vecoapp.android.ui.theme.secondaryBackground
import com.veco.vecoapp.android.ui.theme.spacing

@Composable
fun VecoSuggestionCard(
    modifier: Modifier = Modifier,
    content: @Composable (scope: RowScope) -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.secondaryBackground,
        shape = RoundedCornerShape(MaterialTheme.spacing.small)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            content(this)
            Icon(
                modifier = Modifier.weight(1f),
                painter = painterResource(id = R.drawable.ic_veco_arrow),
                tint = Color.Unspecified,
                contentDescription = null
            )
        }
    }
}
