package com.veco.vecoapp.android.ui.component.misc

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.veco.vecoapp.android.ui.theme.regBody1

@Composable
fun VecoHeadline(text1: String, text2: String) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = text1,
            style = MaterialTheme.typography.h1
        )
        Text(
            text = text2,
            style = MaterialTheme.typography.regBody1
        )
    }
}

@Composable
fun VecoHeadline(text1: String, text2: AnnotatedString) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = text1,
            style = MaterialTheme.typography.h1
        )
        Text(
            text = text2,
            style = MaterialTheme.typography.regBody1
        )
    }
}
