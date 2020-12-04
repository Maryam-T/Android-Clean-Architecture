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

class ListViewModel(application: Application): AndroidViewModel(application) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val repository = NoteRepository(RoomNoteDataSource(application))

    val useCases = UseCases (
            GetNote(repository),
            GetAllNotes(repository),
            AddNote(repository),
            DeleteNote(repository)
    )

    val notes = MutableLiveData<List<Note>>()

    fun getNotes() {
        coroutineScope.launch {
            val notesList = useCases.getAllNotes()
            notes.postValue(notesList)
        }
    }
}