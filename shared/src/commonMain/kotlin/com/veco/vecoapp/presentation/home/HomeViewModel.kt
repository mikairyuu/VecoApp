package com.veco.vecoapp.presentation.home

import com.veco.vecoapp.di.di
import com.veco.vecoapp.domain.entity.Task
import com.veco.vecoapp.domain.entity.enums.ResponseResult
import com.veco.vecoapp.domain.entity.enums.TaskFrequency
import com.veco.vecoapp.domain.entity.enums.TaskStatus
import com.veco.vecoapp.domain.usecase.tasks.GetTasksUseCase
import com.veco.vecoapp.presentation.UIState
import com.veco.vecoapp.presentation.VecoVM
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.kodein.di.instance

class HomeViewModel : VecoVM() {
    val getTasksUseCase: GetTasksUseCase by di.instance()

    private val _taskListState: MutableStateFlow<UIState<List<Task>>> =
        MutableStateFlow(UIState.Idle())
    val taskListState: StateFlow<UIState<List<Task>>> = _taskListState

    val placeholderTask = Task(
        0,
        "Сходить в магазин с собственной сумкой",
        "",
        "Сегодня, 19:00",
        TaskFrequency.values().random(),
        true,
        200,
        TaskStatus.Uncompleted
    )

    init {
        super.proceed(_taskListState, handleErrors = true, request = { getTasksUseCase() }) {
            if (it.resultCode == ResponseResult.Success) {
                _taskListState.value = UIState.Success(it.data!!)
            }
        }
    }
}
