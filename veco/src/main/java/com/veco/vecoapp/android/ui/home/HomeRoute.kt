package com.veco.vecoapp.android.ui.home

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.veco.vecoapp.MR

@Composable
fun HomeRoute() {
    Text(
        text = stringResource(MR.strings.home_title.resourceId),
        style = MaterialTheme.typography.h1
    )
}

@Preview
@Composable
fun HomeRoutePreview() {
    HomeRoute()
}