package com.marand.cleanarchitecture.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.marand.core.data.Note
import com.marand.core.repository.NoteRepository
import com.marand.core.usecase.AddNote
import com.marand.core.usecase.DeleteNote
import com.marand.core.usecase.GetAllNotes
import com.marand.core.usecase.GetNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val repository = NoteRepository(RoomNoteDataSource(application))
    private val useCases = UseCases (
        GetNote(repository),
        GetAllNotes(repository),
        AddNote(repository),
        DeleteNote(repository)
    )

    val saved = MutableLiveData<Boolean>()
    val currentNote = MutableLiveData<Note>()

    fun saveNote(note: Note) {
        coroutineScope.launch {
            useCases.addNote(note)
            saved.postValue(true)
        }
    }

    fun getNote(id: Long) {
        coroutineScope.launch {
            val note = useCases.getNote(id)
            currentNote.postValue(note)
        }
    }

    fun deleteNote(note: Note) {
        coroutineScope.launch {
            useCases.deleteNote(note)
            saved.postValue(true)
        }
    }
}