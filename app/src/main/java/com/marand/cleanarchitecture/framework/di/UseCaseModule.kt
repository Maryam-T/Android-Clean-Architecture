package com.marand.cleanarchitecture.framework.di

import com.marand.cleanarchitecture.framework.UseCases
import com.marand.core.repository.NoteRepository
import com.marand.core.usecase.*
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideUseCase(repository: NoteRepository) = UseCases (
            GetNote(repository),
            GetAllNotes(repository),
            AddNote(repository),
            DeleteNote(repository),
            GetWordCount()
    )
}