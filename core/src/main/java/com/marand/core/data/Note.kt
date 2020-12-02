package com.marand.core.data

data class Note (
    var id: Long = 0,
    var title: String,
    var content: String,
    var creationData: Long,
    var updateDate: Long
)