package com.veco.vecoapp.presentation.home

import com.veco.vecoapp.domain.entity.Task
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

    init {
        viewModelScope.launch {
            _taskListState.value = UIState.Success(getTasksUseCase())
        }
    }
}
