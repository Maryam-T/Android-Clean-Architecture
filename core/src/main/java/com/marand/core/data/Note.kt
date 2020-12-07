package com.marand.core.data

data class Note (
    var title: String,
    var content: String,
    var creationData: Long,
    var updateDate: Long,
    var id: Long = 0L,
    var wordCount: Int = 0
)