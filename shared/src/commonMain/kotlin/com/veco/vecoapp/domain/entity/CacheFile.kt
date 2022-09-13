package com.veco.vecoapp.domain.entity

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class CacheFile : RealmObject {
    @PrimaryKey
    var url: String = ""
    var data: ByteArray? = null
}
