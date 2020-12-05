package com.marand.cleanarchitecture.framework.di

import android.app.Application
import com.marand.cleanarchitecture.framework.RoomNoteDataSource
import com.marand.core.repository.NoteRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideRepository(app: Application) = NoteRepository(RoomNoteDataSource(app))
}