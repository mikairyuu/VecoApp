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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.veco.vecoapp.MR
import com.veco.vecoapp.android.ui.BottomSheetState
import com.veco.vecoapp.android.ui.component.misc.DoubleInfoUnit
import com.veco.vecoapp.android.ui.navigation.Screen
import com.veco.vecoapp.android.ui.theme.body3
import com.veco.vecoapp.android.ui.theme.lightGray
import com.veco.vecoapp.android.ui.theme.orange
import com.veco.vecoapp.android.ui.theme.purple
import com.veco.vecoapp.android.ui.theme.regBody3
import com.veco.vecoapp.android.ui.theme.secondaryText
import com.veco.vecoapp.android.ui.theme.spacing
import com.veco.vecoapp.android.ui.theme.violet
import com.veco.vecoapp.domain.entity.Task
import com.veco.vecoapp.domain.entity.enums.TaskFrequency
import com.veco.vecoapp.domain.entity.enums.TaskStatus
import com.veco.vecoapp.domain.entity.enums.convert
import com.veco.vecoapp.presentation.UIState
import com.veco.vecoapp.presentation.home.HomeViewModel
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeRoute(
    bottomSheetState: MutableState<BottomSheetState>,
    navController: NavController,
    coroutineScope: CoroutineScope,
    viewModel: HomeViewModel = viewModel()
) {
    val selectedTaskSection = remember { mutableStateOf(TaskStatus.Uncompleted) }
    val tasks by viewModel.taskListState.collectAsState()
    LazyColumn {
        when (tasks) {
            is UIState.Loading -> {
                item {
                    Text(text = "Loading...") // TEMP
                }
            }
            is UIState.Success -> {
                item {
                    SectionSelector(selectedTaskSection)
                }
                items((tasks as UIState.Success<List<Task>>).data) {
                    if (it.status == selectedTaskSection.value) {
                        TaskCard(
                            it,
                            bottomSheetState,
                            coroutineScope,
                            navController
                        )
                    }
                }
            }
            is UIState.Error -> {
                item {
                    Text(text = "Error") // TEMP
                }
            }
            is UIState.Idle -> {}
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
                color = if (item == selectedTaskStatus.value) {
                    MaterialTheme.colors.onBackground
                } else {
                    MaterialTheme.colors.lightGray
                },
                style = MaterialTheme.typography.h2
            )
        }
    }
}

@Composable
fun TaskCard(
    task: Task,
    bottomSheetState: MutableState<BottomSheetState>,
    coroutineScope: CoroutineScope,
    navController: NavController
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(if (task.status == TaskStatus.Completed) 0.6f else 1.0f)
            .padding(MaterialTheme.spacing.medium, 0.dp, MaterialTheme.spacing.medium, 12.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp,
        onClick = { showTask(task, bottomSheetState, coroutineScope, context, navController) },
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
    context: Context,
    navController: NavController
) {
    coroutineScope.launch {
        bottomSheetState.value = BottomSheetState(
            state = bottomSheetState.value.state,
            title = task.title,
            desc = task.desc,
            points = task.points,
            deadline = task.deadline,
            frequency = task.frequency.convert().toString(context),
            buttonText = MR.strings.button_next.desc().toString(context),
            onClick = {
                if (!(
                    bottomSheetState.value.state.isAnimationRunning and
                        (bottomSheetState.value.state.currentValue == ModalBottomSheetValue.Hidden)
                    )
                ) {
                    coroutineScope.launch { bottomSheetState.value.state.hide() }
                    navController.navigate(Screen.Confirmation.route)
                }
            }
        )
        bottomSheetState.value.state.show()
    }
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
        ),
        rememberNavController(),
        rememberCoroutineScope()
    )
}
