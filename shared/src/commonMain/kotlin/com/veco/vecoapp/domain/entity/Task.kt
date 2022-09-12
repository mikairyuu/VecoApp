package com.veco.vecoapp.domain.entity

import com.veco.vecoapp.domain.entity.enums.TaskFrequency
import com.veco.vecoapp.domain.entity.enums.TaskStatus
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
class Task : RealmObject {
    @PrimaryKey
    var id: Int = 0
    var title: String = ""
    var description: String = ""
    var deadline: Long = 0
    var type: Int = TaskFrequency.Daily
    var isSeen: Boolean = false
    var points: Int = 0
    var status: Int = TaskStatus.Uncompleted
    @Ignore
    var stringDeadline: String? = null
}
