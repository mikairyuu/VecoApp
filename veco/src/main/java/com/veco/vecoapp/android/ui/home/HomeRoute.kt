package com.veco.vecoapp.android.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.ui.theme.lightGray
import com.veco.vecoapp.android.ui.theme.spacing

val home_sections = listOf(
    MR.strings.main_active.resourceId,
    MR.strings.main_check.resourceId,
    MR.strings.main_finished.resourceId
)

@Composable
fun HomeRoute() {
    Column {
        LazyColumn {
            item {
                SectionSelector()
            }
            items(10) {
                TaskCard()
            }
        }
    }
}

@Composable
fun SectionSelector() {
    var selected by remember { mutableStateOf(0) }
    LazyRow(
        modifier = Modifier.padding(
            MaterialTheme.spacing.medium,
            MaterialTheme.spacing.medium,
            MaterialTheme.spacing.medium,
            12.dp
        )
    ) {
        this.itemsIndexed(home_sections) { index, item ->
            Text(
                modifier = Modifier
                    .clickable { selected = index }
                    .padding(MaterialTheme.spacing.medium, 0.dp),
                text = stringResource(item),
                color = if (index == selected) MaterialTheme.colors.onBackground else
                    MaterialTheme.colors.lightGray,
                style = MaterialTheme.typography.h2
            )
        }
    }
}

@Composable
fun TaskCard() {
    Card(modifier = Modifier.size(200.dp)) {
        Text(text = "Я карта")
    }
}

@Preview
@Composable
fun HomeRoutePreview() {
    HomeRoute()
}