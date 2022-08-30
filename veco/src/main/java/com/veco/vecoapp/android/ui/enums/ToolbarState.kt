package com.veco.vecoapp.android.ui.enums

sealed class ToolbarState {
    object Expandable : ToolbarState()
    object ExpandableExpanded : ToolbarState()
    object Collapsed : ToolbarState()
    class Button(val callback: () -> Unit) : ToolbarState()
    object None : ToolbarState()
}
