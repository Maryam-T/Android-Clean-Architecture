package com.marand.cleanarchitecture.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.marand.cleanarchitecture.framework.di.ApplicationModule
import com.marand.cleanarchitecture.framework.di.DaggerViewModelComponent
import com.marand.core.data.Note
import com.marand.core.repository.NoteRepository
import com.marand.core.usecase.AddNote
import com.marand.core.usecase.DeleteNote
import com.marand.core.usecase.GetAllNotes
import com.marand.core.usecase.GetNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListViewModel(application: Application): AndroidViewModel(application) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var useCases: UseCases

    init {
        DaggerViewModelComponent.builder()
                .applicationModule(ApplicationModule(getApplication()))
                .build()
                .inject(this)
    }

    val notes = MutableLiveData<List<Note>>()

    fun getNotes() {
        coroutineScope.launch {
            val notesList = useCases.getAllNotes()
            notes.postValue(notesList)
        }
    }
}