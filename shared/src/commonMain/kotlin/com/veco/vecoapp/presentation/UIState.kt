package com.veco.vecoapp.presentation

import dev.icerock.moko.resources.desc.StringDesc

sealed class UIState<T> {
    class Idle<T> : UIState<T>()
    class Loading<T> : UIState<T>()
    class Error<T>(val str: StringDesc) : UIState<T>()
    class Success<T>(val data: T) : UIState<T>()
}
