package uz.gita.memory_game_app.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.memory_game_app.domain.repository.AppRepository
import uz.gita.memory_game_app.domain.repository.impl.AppRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModul {

    @[Binds Singleton]
    fun bindAppRepository(impl: AppRepositoryImpl):AppRepository
}