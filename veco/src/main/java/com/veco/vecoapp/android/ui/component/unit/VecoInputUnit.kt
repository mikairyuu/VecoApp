package com.veco.vecoapp.android.ui.component.unit

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.veco.vecoapp.android.ui.component.input.VecoTextField
import com.veco.vecoapp.android.ui.theme.regBody2
import com.veco.vecoapp.android.ui.theme.secondaryText
import com.veco.vecoapp.android.ui.theme.spacing
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * A component that allows the user to enter a value in a text field and display a label to the left
 */
@Composable
fun VecoInputUnit(
    modifier: Modifier = Modifier,
    textState: MutableStateFlow<String>,
    @StringRes title: Int,
    onValueChange: (String) -> Unit = { textState.value = it }
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.mini)
    ) {
        Text(
            style = MaterialTheme.typography.regBody2,
            color = MaterialTheme.colors.secondaryText,
            text = stringResource(id = title)
        )
        VecoTextField(onValueChange = onValueChange, textState = textState)
    }
}
