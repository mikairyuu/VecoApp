package com.veco.vecoapp.android.ui.component.template

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.veco.vecoapp.android.ui.component.input.VecoTextField
import com.veco.vecoapp.android.ui.component.misc.VecoButton
import com.veco.vecoapp.android.ui.component.misc.VecoHeadline
import com.veco.vecoapp.android.ui.theme.spacing
import com.veco.vecoapp.presentation.UIState

@Composable
fun FieldButtonTemplate(
    uiState: UIState<Nothing?>,
    onValueChange: (String) -> Boolean,
    onValueChange2: ((String) -> Boolean)? = null,
    onClick: () -> Unit,
    title: String,
    desc: String,
    buttonText: String,
    hint: String,
    hint2: String? = null
) {
    Column(
        modifier = Modifier.padding(MaterialTheme.spacing.medium, MaterialTheme.spacing.big)
    ) {
        VecoHeadline(
            text1 = title,
            text2 = desc
        )
        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.big))
        VecoTextField(
            onValueChange = onValueChange,
            hint = hint
        )
        onValueChange2?.let {
            Spacer(modifier = Modifier.padding(12.dp))
            VecoTextField(
                onValueChange = onValueChange2,
                hint = hint2 ?: ""
            )
        }
        Spacer(modifier = Modifier.padding(12.dp))
        VecoButton(
            text = buttonText,
            onClick = onClick,
            isLoading = uiState is UIState.Loading
        )
    }
}
