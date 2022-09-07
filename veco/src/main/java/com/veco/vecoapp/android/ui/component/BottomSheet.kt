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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.ui.component.misc.DoubleInfoUnit
import com.veco.vecoapp.android.ui.component.misc.VecoButton
import com.veco.vecoapp.android.ui.theme.regBody1
import com.veco.vecoapp.android.ui.theme.spacing
import com.veco.vecoapp.android.ui.theme.tertiaryText

data class SheetSettings @OptIn(ExperimentalMaterialApi::class)
constructor(
    val state: ModalBottomSheetState,
    val title: String = "",
    val desc: String = "",
    val points: Int = 0,
    val deadline: String = "",
    val frequency: String = "",
    val buttonText: String = "",
    val color: Color? = null, // Change to bitmap later
    val onClick: () -> Unit = {}
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetScaffold(
    bottomSheetState: SheetSettings?,
    content: @Composable () -> Unit
) {
    if (bottomSheetState != null) {
        val photoMode = bottomSheetState.color != null
        ModalBottomSheetLayout(
            sheetState = bottomSheetState.state,
            sheetShape = RoundedCornerShape(8.dp),
            sheetContent = {
                Box(
                    modifier = Modifier
                        .height(if (photoMode) 240.dp else 20.dp)
                        .background(
                            color = bottomSheetState.color ?: MaterialTheme.colors.background
                        )
                        .fillMaxWidth()
                ) {
                    SheetNotch()
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
                        style = MaterialTheme.typography.regBody1
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)) {
                        DoubleInfoUnit(
                            modifier = Modifier.weight(1f),
                            first = stringResource(MR.strings.task_points.resourceId),
                            second = bottomSheetState.points.toString(),
                            needCoin = true,
                            isSmall = false
                        )
                        if (bottomSheetState.deadline != "") {
                            DoubleInfoUnit(
                                modifier = Modifier.weight(1f),
                                first = stringResource(MR.strings.task_deadline.resourceId),
                                second = bottomSheetState.deadline,
                                isSmall = false
                            )
                        }
                    }
                    if (bottomSheetState.frequency != "") {
                        DoubleInfoUnit(
                            first = stringResource(MR.strings.task_type.resourceId),
                            second = bottomSheetState.frequency,
                            isSmall = false
                        )
                    }
                }
                VecoButton(
                    modifier = Modifier.padding(MaterialTheme.spacing.medium),
                    text = bottomSheetState.buttonText,
                    onClick = bottomSheetState.onClick
                )
            }
        ) {
            content()
        }
    } else {
        content()
    }
}

@Composable
fun SheetNotch() {
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
}
