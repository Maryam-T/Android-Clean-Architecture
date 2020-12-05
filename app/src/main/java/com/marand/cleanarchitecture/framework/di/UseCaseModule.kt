package com.marand.cleanarchitecture.framework.di

import com.marand.cleanarchitecture.framework.UseCases
import com.marand.core.repository.NoteRepository
import com.marand.core.usecase.AddNote
import com.marand.core.usecase.DeleteNote
import com.marand.core.usecase.GetAllNotes
import com.marand.core.usecase.GetNote
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideUseCase(repository: NoteRepository) = UseCases (
            GetNote(repository),
            GetAllNotes(repository),
            AddNote(repository),
            DeleteNote(repository)
    )
}