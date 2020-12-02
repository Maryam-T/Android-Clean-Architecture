package com.marand.core.usecase

import com.marand.core.data.Note
import com.marand.core.repository.NoteRepository

class AddNote(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(note: Note) = noteRepository.addNote(note)
}