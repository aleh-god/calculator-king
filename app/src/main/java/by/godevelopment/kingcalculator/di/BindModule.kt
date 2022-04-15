package by.godevelopment.kingcalculator.di

import by.godevelopment.kingcalculator.data.repositories.PlayerRepositoriesImpl
import by.godevelopment.kingcalculator.domain.repositories.PlayerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class BindModule {

    @Binds
    abstract fun bindPlayerRepository(playerRepositoriesImpl: PlayerRepositoriesImpl): PlayerRepository
}