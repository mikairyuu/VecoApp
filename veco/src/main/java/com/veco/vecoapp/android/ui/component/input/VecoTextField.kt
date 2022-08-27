package com.veco.vecoapp.android.ui.component.input

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.veco.vecoapp.android.ui.theme.regBody1
import com.veco.vecoapp.android.ui.theme.secondaryBackground
import com.veco.vecoapp.android.ui.theme.secondaryText
import com.veco.vecoapp.android.ui.theme.spacing

@Composable
fun VecoTextField(
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    keyboardType: KeyboardType = KeyboardType.Text,
    hint: String = "",
    onValueChange: (String) -> Boolean
) {
    val textFieldInteraction = remember { MutableInteractionSource() }
    val textFieldFocused = textFieldInteraction.collectIsFocusedAsState().value
    var text by remember { mutableStateOf("") }
    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(shape = RoundedCornerShape(MaterialTheme.spacing.small))
            .background(color = MaterialTheme.colors.secondaryBackground)
            .padding(12.dp),
        value = text,
        onValueChange = { if (onValueChange(it)) text = it },
        textStyle = MaterialTheme.typography.regBody1,
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = keyboardType),
        decorationBox = {
            if (!textFieldFocused) {
                Text(
                    text = hint,
                    style = MaterialTheme.typography.regBody1,
                    color = MaterialTheme.colors.secondaryText
                )
            } else {
                it()
            }
        },
        interactionSource = textFieldInteraction
    )
}
