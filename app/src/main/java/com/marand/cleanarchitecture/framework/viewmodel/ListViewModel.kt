package com.marand.cleanarchitecture.framework.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.marand.cleanarchitecture.framework.UseCases
import com.marand.cleanarchitecture.framework.di.ApplicationModule
import com.marand.cleanarchitecture.framework.di.DaggerViewModelComponent
import com.marand.core.data.Note
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
            notesList.forEach { it.wordCount = useCases.getWordCount.invoke(it) }
            notes.postValue(notesList)
        }
    }
}