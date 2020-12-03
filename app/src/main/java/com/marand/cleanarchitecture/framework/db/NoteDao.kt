package com.marand.cleanarchitecture.framework.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import retrofit2.http.DELETE

@Dao
interface NoteDao {
    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteEntity(id: Long): NoteEntity?

    @Query("SELECT * FROM note")
    suspend fun getAllNoteEntities(): List<NoteEntity>

    @Insert(onConflict = REPLACE)
    suspend fun addNoteEntity(noteEntity: NoteEntity)

    @Delete
    suspend fun deleteNoteEntity(noteEntity: NoteEntity)
}