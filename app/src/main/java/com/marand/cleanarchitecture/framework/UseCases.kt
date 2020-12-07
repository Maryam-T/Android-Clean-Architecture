package com.marand.cleanarchitecture.framework

import com.marand.core.usecase.*

data class UseCases(
    val getNote: GetNote,
    val getAllNotes: GetAllNotes,
    val addNote: AddNote,
    val deleteNote: DeleteNote,
    val getWordCount: GetWordCount
)