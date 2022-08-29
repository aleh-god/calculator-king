package by.godevelopment.kingcalculator.di

import by.godevelopment.kingcalculator.data.repositories.GameRepositoryImpl
import by.godevelopment.kingcalculator.data.repositories.PartyRepositoryImpl
import by.godevelopment.kingcalculator.data.repositories.PlayerRepositoryImpl
import by.godevelopment.kingcalculator.data.repositories.TricksRepositoryImpl
import by.godevelopment.kingcalculator.domain.gamesdomain.repositories.GameRepository
import by.godevelopment.kingcalculator.domain.partiesdomain.repositories.PartyRepository
import by.godevelopment.kingcalculator.domain.playersdomain.repositories.PlayerCardRepository
import by.godevelopment.kingcalculator.domain.playersdomain.repositories.PlayerRepository
import by.godevelopment.kingcalculator.domain.settingsdomain.repositories.DeleteGamesRepository
import by.godevelopment.kingcalculator.domain.settingsdomain.repositories.DeletePartiesRepository
import by.godevelopment.kingcalculator.domain.settingsdomain.repositories.DeletePlayersRepository
import by.godevelopment.kingcalculator.domain.settingsdomain.repositories.DeleteTricksRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class BindModule {

    @Binds
    abstract fun bindPlayerRepository(playerRepositoryImpl: PlayerRepositoryImpl): PlayerRepository

    @Binds
    abstract fun bindPlayerCardRepository(playerRepositoryImpl: PlayerRepositoryImpl): PlayerCardRepository

    @Binds
    abstract fun bindPartyRepository(partyRepositoryImpl: PartyRepositoryImpl): PartyRepository

    @Binds
    abstract fun bindGameRepository(gameRepositoryImpl: GameRepositoryImpl): GameRepository

    @Binds
    abstract fun bindDeletePlayersRepository(playerRepositoryImpl: PlayerRepositoryImpl): DeletePlayersRepository

    @Binds
    abstract fun bindDeletePartiesRepository(partyRepositoryImpl: PartyRepositoryImpl): DeletePartiesRepository

    @Binds
    abstract fun bindDeleteGamesRepository(gameRepositoryImpl: GameRepositoryImpl): DeleteGamesRepository

    @Binds
    abstract fun bindDeleteTricksRepository(tricksRepositoryImpl: TricksRepositoryImpl): DeleteTricksRepository
}
