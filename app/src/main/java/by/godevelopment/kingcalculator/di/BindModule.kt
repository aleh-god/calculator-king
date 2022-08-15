package by.godevelopment.kingcalculator.di

import by.godevelopment.kingcalculator.data.repositories.GameRepositoryImpl
import by.godevelopment.kingcalculator.data.repositories.PartyRepositoryImpl
import by.godevelopment.kingcalculator.data.repositories.PlayerRepositoryImpl
import by.godevelopment.kingcalculator.domain.gamesdomain.repositories.GameRepository
import by.godevelopment.kingcalculator.domain.partiesdomain.repositories.PartyRepository
import by.godevelopment.kingcalculator.domain.playersdomain.repositories.PlayerRepository
import by.godevelopment.kingcalculator.presentation.mainactivity.MainActivityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class BindModule {

    @Binds
    abstract fun bindMainActivityRepository(partyRepositoryImpl: PartyRepositoryImpl): MainActivityRepository

    @Binds
    abstract fun bindPlayerRepository(playerRepositoryImpl: PlayerRepositoryImpl): PlayerRepository

    @Binds
    abstract fun bindPartyRepository(partyRepositoryImpl: PartyRepositoryImpl): PartyRepository

    @Binds
    abstract fun bindGameRepository(gameRepositoryImpl: GameRepositoryImpl): GameRepository
}