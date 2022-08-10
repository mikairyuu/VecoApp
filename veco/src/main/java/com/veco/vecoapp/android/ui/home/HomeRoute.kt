package com.veco.vecoapp.android.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.R
import com.veco.vecoapp.android.ui.theme.body3
import com.veco.vecoapp.android.ui.theme.lightGray
import com.veco.vecoapp.android.ui.theme.regBody1
import com.veco.vecoapp.android.ui.theme.regBody3
import com.veco.vecoapp.android.ui.theme.secondaryText
import com.veco.vecoapp.android.ui.theme.spacing
import com.veco.vecoapp.android.ui.theme.violet
import com.veco.vecoapp.android.ui.utils.defaultGradient
import com.veco.vecoapp.getStr

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
            0.dp,
            MaterialTheme.spacing.medium,
            0.dp,
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.medium, 0.dp, MaterialTheme.spacing.medium, 12.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp
    ) {
        Box(modifier = Modifier.padding(12.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = stringResource(MR.strings.task_daily.resourceId),
                    color = MaterialTheme.colors.violet,
                    style = MaterialTheme.typography.body3
                )
                Text(
                    text = "Сходить в магазин с собственной сумкой",
                    style = MaterialTheme.typography.body1
                )
                Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)) {
                    Column {
                        Text(
                            text = stringResource(MR.strings.task_deadline.resourceId),
                            color = MaterialTheme.colors.secondaryText,
                            style = MaterialTheme.typography.regBody3
                        )
                        Text(
                            text = "Сегодня, 19:00",
                            style = MaterialTheme.typography.body2
                        )
                    }
                    Column {
                        Text(
                            text = stringResource(MR.strings.task_points.resourceId),
                            color = MaterialTheme.colors.secondaryText,
                            style = MaterialTheme.typography.regBody3,
                            textAlign = TextAlign.Start
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                            Text(
                                text = "800",
                                style = MaterialTheme.typography.body2
                            )
                            Icon(
                                modifier = Modifier.align(Alignment.CenterVertically),
                                painter = painterResource(R.drawable.ic_veco_pts_clr),
                                tint = Color.Unspecified,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeRoutePreview() {
    HomeRoute()
}