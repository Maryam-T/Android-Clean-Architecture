package com.marand.cleanarchitecture.framework

import android.content.Context
import com.marand.cleanarchitecture.framework.db.DatabaseService
import com.marand.cleanarchitecture.framework.db.NoteEntity
import com.marand.core.data.Note
import com.marand.core.repository.NoteDataSource

class RoomNoteDataSource(context: Context): NoteDataSource {
    val noteDao = DatabaseService.getInstance(context).noteDao()

    override suspend fun get(id: Long) = noteDao.getNoteEntity(id)?.toNote()

    override suspend fun getAll() = noteDao.getAllNoteEntities().map { it.toNote() }

    override suspend fun add(note: Note) = noteDao.addNoteEntity(NoteEntity.fromNote(note))

    override suspend fun delete(note: Note) = noteDao.deleteNoteEntity(NoteEntity.fromNote(note))
}