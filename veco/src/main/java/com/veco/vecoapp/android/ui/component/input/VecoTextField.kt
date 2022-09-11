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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.veco.vecoapp.android.ui.theme.regBody1
import com.veco.vecoapp.android.ui.theme.secondaryBackground
import com.veco.vecoapp.android.ui.theme.secondaryText
import com.veco.vecoapp.android.ui.theme.spacing
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun VecoTextField(
    modifier: Modifier = Modifier,
    textState: MutableStateFlow<String>,
    singleLine: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    keyboardType: KeyboardType = KeyboardType.Text,
    hint: String = "",
    isError: Boolean = false,
    decorationBox: @Composable (text: String, isFocused: Boolean, textFunc: @Composable () -> Unit) -> Unit =
        { text, isFocused, textFunc ->
            if (text.isEmpty() && !isFocused) {
                Text(
                    text = hint,
                    style = MaterialTheme.typography.regBody1,
                    color = MaterialTheme.colors.secondaryText
                )
            } else {
                textFunc()
            }
        },
    onValueChange: (String) -> Unit = { textState.value = it }
) {
    val textFieldInteraction = remember { MutableInteractionSource() }
    val textFieldFocused = textFieldInteraction.collectIsFocusedAsState().value
    val text by textState.collectAsState()
    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(shape = RoundedCornerShape(MaterialTheme.spacing.small))
            .background(
                color = if (isError) {
                    MaterialTheme.colors.error
                } else {
                    MaterialTheme.colors.secondaryBackground
                }
            )
            .padding(12.dp),
        value = text,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.regBody1,
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = keyboardType),
        decorationBox = {
            decorationBox(text, textFieldFocused, it)
        },
        interactionSource = textFieldInteraction,
        visualTransformation = if (keyboardType == KeyboardType.Password) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        }
    )
}
