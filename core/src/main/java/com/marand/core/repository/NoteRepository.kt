package com.marand.core.repository

import com.marand.core.data.Note

class NoteRepository(private val dataSource: NoteDataSource) {
    suspend fun getNote(id: Long) = dataSource.get(id)

    suspend fun getAllNotes() = dataSource.getAll()

    suspend fun addNote(note: Note) = dataSource.add(note)

    suspend fun deleteNote(note: Note) = dataSource.delete(note)
}