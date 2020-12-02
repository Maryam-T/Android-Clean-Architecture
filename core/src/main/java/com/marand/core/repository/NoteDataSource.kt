package com.marand.core.repository

import com.marand.core.data.Note

interface NoteDataSource {
    suspend fun get(id: Long): Note?

    suspend fun getAll(): List<Note>

    suspend fun add(note: Note)

    suspend fun delete(note: Note)
}