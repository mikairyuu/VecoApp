package com.veco.vecoapp.android.ui.component.input

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.veco.vecoapp.android.R
import com.veco.vecoapp.android.ui.theme.regBody1
import com.veco.vecoapp.android.ui.theme.secondaryText
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun VecoSearchTextField(
    modifier: Modifier = Modifier,
    textState: MutableStateFlow<String>,
    singleLine: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    keyboardType: KeyboardType = KeyboardType.Text,
    hint: String = "",
    isError: Boolean = false,
    decorationBox: @Composable (text: String, isFocused: Boolean, textFunc: @Composable () -> Unit) -> Unit =
        { text, isFocused, textFunc ->
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(6.dp))
                if (text.isEmpty() && !isFocused) {
                    Text(
                        text = hint,
                        style = MaterialTheme.typography.regBody1,
                        color = MaterialTheme.colors.secondaryText
                    )
                } else {
                    textFunc()
                }
                Spacer(modifier = Modifier.width(6.dp))
                if (text.isNotEmpty()) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Icon(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .requiredWidth(24.dp)
                                .clickable { textState.value = "" },
                            painter = painterResource(id = R.drawable.ic_close_btn),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                }
            }
        },
    onValueChange: (String) -> Unit = { textState.value = it }
) {
    VecoTextField(
        modifier = modifier,
        textState = textState,
        singleLine = singleLine,
        imeAction = imeAction,
        keyboardType = keyboardType,
        isError = isError,
        decorationBox = { text, isFocused, textFunc ->
            decorationBox(text, isFocused) {
                textFunc()
            }
        },
        onValueChange = onValueChange
    )
}
