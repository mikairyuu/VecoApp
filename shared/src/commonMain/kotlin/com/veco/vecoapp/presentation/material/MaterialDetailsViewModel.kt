package com.veco.vecoapp.presentation.material

import com.veco.vecoapp.di.di
import com.veco.vecoapp.domain.entity.Material
import com.veco.vecoapp.domain.usecase.material.GetMaterialUseCase
import com.veco.vecoapp.presentation.UIState
import com.veco.vecoapp.presentation.VecoVM
import com.veco.vecoapp.utils.MaterialElement
import com.veco.vecoapp.utils.MaterialTextParser
import com.veco.vecoapp.utils.getDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.kodein.di.instance

class MaterialDetailsViewModel : VecoVM() {
    val getMaterialUseCase: GetMaterialUseCase by di.instance()

    private val _materialState: MutableStateFlow<UIState<Material>> =
        MutableStateFlow(UIState.Idle())
    val materialState: StateFlow<UIState<Material>> = _materialState

    var parsedText: List<MaterialElement> = emptyList()

    fun getMaterial(id: Int) {
        proceed(_materialState, { getMaterialUseCase(id, true) }, handleErrors = true) {
            _materialState.emit(UIState.Success(it.data!!))
            parsedText = MaterialTextParser.parse(it.data.description)
            it.data.stringDate = getDate(it.data.date)
        }
    }
}
