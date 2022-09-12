package com.veco.vecoapp.presentation.home

import com.veco.vecoapp.di.di
import com.veco.vecoapp.domain.entity.Task
import com.veco.vecoapp.domain.entity.enums.ResponseResult
import com.veco.vecoapp.domain.entity.enums.TaskFrequency
import com.veco.vecoapp.domain.entity.enums.TaskStatus
import com.veco.vecoapp.domain.usecase.tasks.GetTasksUseCase
import com.veco.vecoapp.presentation.UIState
import com.veco.vecoapp.presentation.VecoVM
import com.veco.vecoapp.utils.getDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.kodein.di.instance

class HomeViewModel : VecoVM() {
    val getTasksUseCase: GetTasksUseCase by di.instance()

    private val _taskListState: MutableStateFlow<UIState<List<Task>>> =
        MutableStateFlow(UIState.Idle())
    val taskListState: StateFlow<UIState<List<Task>>> = _taskListState

    private var isFirstLaunch = true

    val placeholderTask = Task().apply {
        id = 0
        title = "Сходить в магазин с собственной сумкой"
        description = ""
        deadline = 1662854400
        type = TaskFrequency.Daily
        isSeen = true
        points = 200
        status = TaskStatus.Uncompleted
        stringDeadline = "Сегодня, 19:00"
    }

    fun onRefresh(forceSync: Boolean = false) {
        super.proceed(
            _taskListState,
            handleErrors = true,
            request = { getTasksUseCase(isFirstLaunch || forceSync) }
        ) {
            isFirstLaunch = false
            if (it.resultCode == ResponseResult.Success) {
                it.data!!.forEach { it.stringDeadline = getDate(it.deadline) }
                _taskListState.value = UIState.Success(it.data)
            }
        }
    }
}
