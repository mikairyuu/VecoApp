package com.veco.vecoapp.presentation

sealed class UIState<T> {
    class Idle<T> : UIState<T>()
    class Loading<T> : UIState<T>()
    class Error<T> : UIState<T>()
    class Success<T>(val data: T) : UIState<T>()
}
