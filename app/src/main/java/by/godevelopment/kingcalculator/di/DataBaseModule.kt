package by.godevelopment.kingcalculator.di

import android.content.Context
import androidx.room.Room
import by.godevelopment.kingcalculator.commons.DB_NAME
import by.godevelopment.kingcalculator.data.database.GamesDao
import by.godevelopment.kingcalculator.data.database.KingDatabase
import by.godevelopment.kingcalculator.data.database.PartiesDao
import by.godevelopment.kingcalculator.data.database.PlayersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
    @Singleton
    fun provideKingDatabase(
        @ApplicationContext appContext: Context
    ): KingDatabase = Room.databaseBuilder(
        appContext,
        KingDatabase::class.java,
        DB_NAME
    )
        .build()
}