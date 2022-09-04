package com.veco.vecoapp.android.ui.component.template

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.veco.vecoapp.android.ui.component.input.VecoTextField
import com.veco.vecoapp.android.ui.component.misc.VecoButton
import com.veco.vecoapp.android.ui.component.misc.VecoHeadline
import com.veco.vecoapp.android.ui.theme.spacing
import com.veco.vecoapp.presentation.UIState
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun FieldButtonTemplate(
    uiState: UIState<Nothing?>,
    textState: MutableStateFlow<String>,
    textState2: MutableStateFlow<String>? = null,
    onClick: () -> Unit,
    onProceed: () -> Unit,
    title: String,
    desc: String,
    buttonText: String,
    hint: String,
    hint2: String? = null
) {
    val context = LocalContext.current
    LaunchedEffect(uiState) {
        when (uiState) {
            is UIState.Success -> {
                onProceed()
            }
            is UIState.Error -> {
                Toast.makeText(context, "", Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }
    Column(
        modifier = Modifier.padding(MaterialTheme.spacing.medium, MaterialTheme.spacing.big)
    ) {
        VecoHeadline(
            text1 = title,
            text2 = desc
        )
        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.big))
        VecoTextField(
            hint = hint,
            textState = textState
        )
        textState2?.let {
            Spacer(modifier = Modifier.padding(12.dp))
            VecoTextField(
                hint = hint2 ?: "",
                textState = textState2
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
