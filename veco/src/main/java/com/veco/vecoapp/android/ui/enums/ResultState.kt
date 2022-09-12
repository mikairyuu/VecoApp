package com.veco.vecoapp.android.ui.enums

sealed class ResultState {
    class LOADING(val progress: Float) : ResultState()
    object SUCCESS : ResultState()
    object ERROR : ResultState()
    object CANCELED : ResultState()
    object NONE : ResultState()
}
