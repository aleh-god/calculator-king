package by.godevelopment.kingcalculator.di

import by.godevelopment.kingcalculator.data.repositories.GameRepositoryImpl
import by.godevelopment.kingcalculator.domain.gamesdomain.repositories.GetMultiItemModelsRepository
import by.godevelopment.kingcalculator.domain.gamesdomain.repositories.SaveGameRepository
import by.godevelopment.kingcalculator.domain.gamesdomain.repositories.GetPartyIdByGameIdRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class GamesBindModule {

    @Binds
    abstract fun bindGetMultiItemModelsRepository(gameRepositoryImpl: GameRepositoryImpl): GetMultiItemModelsRepository

    @Binds
    abstract fun bindGetPartyIdByGameIdRepository(gameRepositoryImpl: GameRepositoryImpl): GetPartyIdByGameIdRepository

    @Binds
    abstract fun bindSaveGameRepository(gameRepositoryImpl: GameRepositoryImpl): SaveGameRepository
}