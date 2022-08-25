package com.veco.vecoapp.presentation.home

import com.veco.vecoapp.domain.entity.Task
import com.veco.vecoapp.domain.entity.enums.TaskFrequency
import com.veco.vecoapp.domain.entity.enums.TaskStatus
import com.veco.vecoapp.domain.usecase.tasks.GetTasksUseCase
import com.veco.vecoapp.presentation.UIState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(getTasksUseCase: GetTasksUseCase = GetTasksUseCase()) : ViewModel() {

    private val _taskListState: MutableStateFlow<UIState<List<Task>>> =
        MutableStateFlow(UIState.Loading())
    val taskListState: StateFlow<UIState<List<Task>>> = _taskListState

    val placeholderTask = Task(
        "Сходить в магазин с собственной сумкой",
        "",
        "Сегодня, 19:00",
        TaskFrequency.values().random(),
        200,
        TaskStatus.Uncompleted
    )

    init {
        viewModelScope.launch {
            _taskListState.value = UIState.Success(getTasksUseCase())
        }
    }
}
