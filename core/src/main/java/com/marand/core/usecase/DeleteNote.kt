package com.marand.core.usecase

import com.marand.core.data.Note
import com.marand.core.repository.NoteRepository

class DeleteNote(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(note: Note) = noteRepository.deleteNote(note)
}