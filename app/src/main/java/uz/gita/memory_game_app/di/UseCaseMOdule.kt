package uz.gita.memory_game_app.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.memory_game_app.domain.usecase.UseCaseGame
import uz.gita.memory_game_app.domain.usecase.impl.UseCaseGameImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseMOdule {
    @Binds
    fun bindGameUseCase(impl: UseCaseGameImpl):UseCaseGame
}