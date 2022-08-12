@file:OptIn(ExperimentalMaterialApi::class)

package com.veco.vecoapp.android.ui.screen.home

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.ui.BottomSheetState
import com.veco.vecoapp.android.ui.component.misc.DoubleInfoUnit
import com.veco.vecoapp.android.ui.theme.body3
import com.veco.vecoapp.android.ui.theme.lightGray
import com.veco.vecoapp.android.ui.theme.orange
import com.veco.vecoapp.android.ui.theme.purple
import com.veco.vecoapp.android.ui.theme.regBody3
import com.veco.vecoapp.android.ui.theme.secondaryText
import com.veco.vecoapp.android.ui.theme.spacing
import com.veco.vecoapp.android.ui.theme.violet
import com.veco.vecoapp.dto.Task
import com.veco.vecoapp.enums.TaskFrequency
import com.veco.vecoapp.enums.TaskStatus
import com.veco.vecoapp.enums.convert
import dev.icerock.moko.resources.compose.localized
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun HomeRoute(bottomSheetState: MutableState<BottomSheetState>) {
    val coroutineScope = rememberCoroutineScope()
    val selectedTaskStatus = remember { mutableStateOf(TaskStatus.Uncompleted) }
    Column {
        LazyColumn {
            item {
                SectionSelector(selectedTaskStatus)
            }
            items(10) {
                TaskCard(getTestTask(selectedTaskStatus.value), bottomSheetState, coroutineScope)
            }
        }
    }
}

@Composable
fun SectionSelector(selectedTaskStatus: MutableState<TaskStatus>) {
    LazyRow(
        modifier = Modifier.padding(
            0.dp,
            MaterialTheme.spacing.medium,
            0.dp,
            12.dp
        )
    ) {
        items(TaskStatus.values()) { item ->
            Text(
                modifier = Modifier
                    .clickable { selectedTaskStatus.value = item }
                    .padding(MaterialTheme.spacing.medium, 0.dp),
                text = item.convert().localized(),
                color = if (item == selectedTaskStatus.value) MaterialTheme.colors.onBackground else
                    MaterialTheme.colors.lightGray,
                style = MaterialTheme.typography.h2
            )
        }
    }
}

@Composable
fun TaskCard(
    task: Task,
    bottomSheetState: MutableState<BottomSheetState>,
    coroutineScope: CoroutineScope
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(if (task.status == TaskStatus.Completed) 0.6f else 1.0f)
            .padding(MaterialTheme.spacing.medium, 0.dp, MaterialTheme.spacing.medium, 12.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp,
        onClick = { showTask(task, bottomSheetState, coroutineScope, context) },
        enabled = task.status != TaskStatus.Completed
    ) {
        Box(modifier = Modifier.padding(12.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterStart),
                        text = task.frequency.convert().localized(),
                        color = when (task.frequency) {
                            TaskFrequency.Daily -> MaterialTheme.colors.violet
                            TaskFrequency.Weekly -> MaterialTheme.colors.purple
                            TaskFrequency.Monthly -> MaterialTheme.colors.orange
                        },
                        style = MaterialTheme.typography.body3
                    )
                    if (task.status == TaskStatus.Completed) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            text = stringResource(id = MR.strings.task_completed.resourceId),
                            color = MaterialTheme.colors.secondaryText,
                            style = MaterialTheme.typography.regBody3
                        )
                    }
                }
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.body1
                )
                Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)) {
                    DoubleInfoUnit(
                        modifier = Modifier.weight(1f),
                        first = stringResource(MR.strings.task_deadline.resourceId),
                        second = task.deadline
                    )
                    DoubleInfoUnit(
                        modifier = Modifier.weight(1f),
                        first = stringResource(MR.strings.task_points.resourceId),
                        second = task.points.toString(),
                        needCoin = true
                    )
                }
            }
        }
    }
}

fun showTask(
    task: Task,
    bottomSheetState: MutableState<BottomSheetState>,
    coroutineScope: CoroutineScope,
    context: Context
) {
    coroutineScope.launch {
        bottomSheetState.value = BottomSheetState(
            state = bottomSheetState.value.state,
            title = task.title,
            desc = task.desc,
            points = task.points,
            deadline = task.deadline,
            frequency = task.frequency.convert().toString(context),
            buttonText = task.frequency.convert().toString(context),
        )
        bottomSheetState.value.state.show()
    }
}

fun getTestTask(status: TaskStatus): Task {
    return Task(
        "Сходить в магазин с собственной сумкой",
        "Огромное количество людей летом проводят свой отдых в парке, часто забывая про" +
            " санитарные правила, станьте волонтером и очистите свой парк от мусора",
        "Сегодня, 19:00",
        TaskFrequency.values().random(),
        Random.nextInt(200, 2000),
        status
    )
}

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun HomeRoutePreview() {
    HomeRoute(
        mutableStateOf(
            BottomSheetState(
                rememberModalBottomSheetState(
                    initialValue =
                    ModalBottomSheetValue.Hidden
                )
            )
        )
    )
}
