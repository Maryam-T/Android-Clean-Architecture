package com.marand.cleanarchitecture.framework.di

import com.marand.cleanarchitecture.framework.ListViewModel
import com.marand.cleanarchitecture.framework.NoteViewModel
import dagger.Component

@Component(modules = [ApplicationModule::class, RepositoryModule::class, UseCaseModule::class])
interface ViewModelComponent {

    fun inject(noteViewModel: NoteViewModel)

    fun inject(listViewModel: ListViewModel)
}