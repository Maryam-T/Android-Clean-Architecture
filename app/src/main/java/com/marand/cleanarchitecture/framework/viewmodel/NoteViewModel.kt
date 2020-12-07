package com.marand.cleanarchitecture.framework.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.marand.cleanarchitecture.framework.UseCases
import com.marand.cleanarchitecture.framework.di.ApplicationModule
import com.marand.cleanarchitecture.framework.di.DaggerViewModelComponent
import com.marand.core.data.Note
import com.marand.core.usecase.DeleteNote
import com.marand.core.usecase.GetAllNotes
import com.marand.core.usecase.GetNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteViewModel(application: Application): AndroidViewModel(application) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var useCases: UseCases

    init {
        DaggerViewModelComponent.builder()
                .applicationModule(ApplicationModule(getApplication()))
                .build()
                .inject(this)
    }

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