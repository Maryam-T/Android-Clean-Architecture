package com.marand.cleanarchitecture.framework.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marand.core.data.Note

@Entity(tableName = "note")
data class NoteEntity (
    val title: String,
    val content: String,
    val creationData: Long,
    val updateDate: Long,

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L
) {
    companion object {
        fun fromNote(note: Note) = NoteEntity(note.title, note.content, note.creationData, note.updateDate, note.id)
    }

    fun toNote() = Note(title, content, creationData, updateDate, id)
}