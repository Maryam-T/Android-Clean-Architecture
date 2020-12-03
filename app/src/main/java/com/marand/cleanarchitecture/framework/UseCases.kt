package com.marand.cleanarchitecture.framework

import com.marand.core.usecase.AddNote
import com.marand.core.usecase.DeleteNote
import com.marand.core.usecase.GetAllNotes
import com.marand.core.usecase.GetNote

data class UseCases(
    val getNote: GetNote,
    val getAllNotes: GetAllNotes,
    val addNote: AddNote,
    val deleteNote: DeleteNote
)