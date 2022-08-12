package com.veco.vecoapp.android.ui.component.unit

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import com.veco.vecoapp.android.ui.component.input.VecoTextField
import com.veco.vecoapp.android.ui.theme.regBody2
import com.veco.vecoapp.android.ui.theme.secondaryText
import com.veco.vecoapp.android.ui.theme.spacing

@Composable
fun VecoInputUnit(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    textFieldValue: MutableState<TextFieldValue>,
    onValueChange: (TextFieldValue) -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.mini)
    ) {
        Text(
            style = MaterialTheme.typography.regBody2, color = MaterialTheme.colors.secondaryText,
            text = stringResource(id = title)
        )
        VecoTextField(textFieldValue = textFieldValue, onValueChange = onValueChange)
    }
}
