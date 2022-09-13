package com.veco.vecoapp.domain.entity

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
class Material : RealmObject {
    @PrimaryKey
    var id: Int = 0
    var author: String = ""
    var title: String = ""
    var description: String = ""
    var date: Long = 0
    var imagePaths: List<String> = emptyList()
    var imageTypes: List<Int> = emptyList()
    var category: Int = 0

    @Ignore
    var stringDate: String = ""
}
