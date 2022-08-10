package com.veco.vecoapp.android.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.R
import com.veco.vecoapp.android.ui.BottomSheetState
import com.veco.vecoapp.android.ui.component.misc.DoubleInfoUnit
import com.veco.vecoapp.android.ui.component.misc.VecoButton
import com.veco.vecoapp.android.ui.theme.regBody2
import com.veco.vecoapp.android.ui.theme.regBody3
import com.veco.vecoapp.android.ui.theme.secondaryText
import com.veco.vecoapp.android.ui.theme.spacing
import com.veco.vecoapp.android.ui.theme.tertiaryText

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetScaffold(
    bottomSheetState: BottomSheetState,
    content: @Composable () -> Unit
) {
    ModalBottomSheetLayout(
        sheetState = bottomSheetState.state,
        sheetShape = RoundedCornerShape(8.dp),
        sheetContent = {
            Box(
                Modifier
                    .padding(0.dp, 12.dp, 0.dp, 4.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp, 4.dp)
                        .background(MaterialTheme.colors.tertiaryText, RoundedCornerShape(2.dp))
                        .alpha(0.4f)
                        .align(Alignment.Center)
                )
            }
            Column(
                modifier = Modifier.padding(MaterialTheme.spacing.medium),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = bottomSheetState.title,
                    style = MaterialTheme.typography.h2
                )
                Text(
                    text = bottomSheetState.desc,
                    style = MaterialTheme.typography.body1
                )
                Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)) {
                    DoubleInfoUnit(
                        first = stringResource(MR.strings.task_points.resourceId),
                        second = bottomSheetState.points.toString(),
                        needCoin = true,
                        isSmall = false
                    )
                    DoubleInfoUnit(
                        first = stringResource(MR.strings.task_deadline.resourceId),
                        second = bottomSheetState.deadline,
                        isSmall = false
                    )
                }
                DoubleInfoUnit(
                    first = stringResource(MR.strings.task_type.resourceId),
                    second = bottomSheetState.frequency,
                    isSmall = false
                )
            }
            VecoButton(text = bottomSheetState.buttonText, onClick = bottomSheetState.onClick)
        }
    ) {
        content()
    }
}