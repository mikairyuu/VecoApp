package com.veco.vecoapp.android.ui.component.input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.veco.vecoapp.android.ui.theme.regBody1
import com.veco.vecoapp.android.ui.theme.secondaryBackground
import com.veco.vecoapp.android.ui.theme.spacing

@Composable
fun VecoTextField(
    modifier: Modifier = Modifier,
    textFieldValue: MutableState<TextFieldValue>,
    singleLine: Boolean = true,
    onValueChange: (TextFieldValue) -> Unit = {}
) {
    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(MaterialTheme.spacing.small))
            .background(color = MaterialTheme.colors.secondaryBackground)
            .padding(12.dp),
        value = textFieldValue.value,
        onValueChange = { textFieldValue.value = it; onValueChange(it) },
        textStyle = MaterialTheme.typography.regBody1,
        singleLine = singleLine
    )
}