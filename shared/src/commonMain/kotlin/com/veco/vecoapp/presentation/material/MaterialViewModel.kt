package com.veco.vecoapp.presentation.material

import com.veco.vecoapp.di.di
import com.veco.vecoapp.domain.entity.Material
import com.veco.vecoapp.domain.usecase.file.DownloadImageUseCase
import com.veco.vecoapp.domain.usecase.material.GetMaterialPageUseCase
import com.veco.vecoapp.presentation.UIState
import com.veco.vecoapp.presentation.VecoVM
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.kodein.di.instance

class MaterialViewModel : VecoVM() {
    private val pageSize = 5

    val getMaterialPageUseCase: GetMaterialPageUseCase by di.instance()
    val downloadImageUseCase: DownloadImageUseCase by di.instance()

    private val _materialListState: MutableStateFlow<UIState<MutableList<Material>>> =
        MutableStateFlow(UIState.Idle())
    val materialListState: StateFlow<UIState<MutableList<Material>>> = _materialListState

    private var pageCount = 0

    var isNext = true

    fun loadNextPage() {
        super.proceed(
            _materialListState,
            { getMaterialPageUseCase(pageCount, pageSize) },
            handleErrors = true
        ) {
            if (_materialListState.value is UIState.Success) {
                (_materialListState.value as UIState.Success<MutableList<Material>>).data.addAll(
                    pageCount * pageSize,
                    it.data!!.data
                )
            } else {
                _materialListState.emit(UIState.Success(it.data!!.data.toMutableList()))
            }
            isNext = it.data.isNext
            pageCount++
        }
    }

    fun downloadImage(url: String, onDownload: (bytes: ByteArray) -> Unit) {
        super.proceed<Any, ByteArray>(
            null,
            { downloadImageUseCase(url) },
            handleErrors = true
        ) {
            onDownload(it.data!!)
        }
    }

    fun invalidate() {
        pageCount = 0
        _materialListState.value = UIState.Idle()
    }

    val placeholderMaterialList =
        listOf(
            Material().apply {
                title = "Жизнь IT-инженера в Эстонии: тотальная русскоязычность, прекрасная экология"
            },
            Material().apply {
                title = "Экология в моде: Upcycling и recycling одежды и предметов интерьера"
            }
        )
}
