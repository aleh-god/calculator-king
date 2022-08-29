package by.godevelopment.kingcalculator.di

import android.content.Context
import androidx.room.Room
import by.godevelopment.kingcalculator.commons.DB_NAME
import by.godevelopment.kingcalculator.data.database.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    fun providePlayersDao(kingDatabase: KingDatabase): PlayersDao = kingDatabase.playersDao()

    @Provides
    fun providePartiesDao(kingDatabase: KingDatabase): PartiesDao = kingDatabase.partiesDao()

    @Provides
    fun provideGamesDao(kingDatabase: KingDatabase): GamesDao = kingDatabase.gamesDao()

    @Provides
    fun provideTricksDao(kingDatabase: KingDatabase): TricksDao = kingDatabase.tricksDao()

    private val applicationScope = CoroutineScope(SupervisorJob())
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideKingDatabase(
        @ApplicationContext appContext: Context,
        providerPlayers: Provider<PlayersDao>,
        providerParties: Provider<PartiesDao>,
        providerGames: Provider<GamesDao>,
        providerTricks: Provider<TricksDao>
    ): KingDatabase = Room.databaseBuilder(
        appContext,
        KingDatabase::class.java,
        DB_NAME
    )
        .addCallback(PrepopulateCallBack(
            providerPlayers = providerPlayers,
            providerParties = providerParties,
            providerGames = providerGames,
            providerTricks = providerTricks,
            applicationScope = applicationScope,
            dispatcher = dispatcher
        ))
        .build()
}
