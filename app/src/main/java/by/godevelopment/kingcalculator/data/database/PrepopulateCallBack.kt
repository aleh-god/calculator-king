package by.godevelopment.kingcalculator.data.database

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import by.godevelopment.kingcalculator.commons.KingDataTest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Provider

class PrepopulateCallBack(
    private val providerPlayers: Provider<PlayersDao>,
    private val providerParties: Provider<PartiesDao>,
    private val providerGames: Provider<GamesDao>,
    private val providerTricks: Provider<TricksDao>,
    private val applicationScope: CoroutineScope,
    private val dispatcher: CoroutineDispatcher
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        applicationScope.launch(dispatcher) {
            addPlayers()
            addParty()
            addGames()
            addTricks()
        }
    }

    private suspend fun addTricks() {
        KingDataTest.tricks.forEach {
            providerTricks.get().insertTricksNote(it)
        }
    }

    private suspend fun addGames() {
        KingDataTest.games.forEach {
            providerGames.get().insertGamesNote(it)
        }
    }

    private suspend fun addParty() {
        providerParties.get().insertPartyNote(KingDataTest.party)
    }

    private suspend fun addPlayers() {
        providerPlayers.get().insertPlayerProfile(KingDataTest.playerDonProfile)
        providerPlayers.get().insertPlayerProfile(KingDataTest.playerLeoProfile)
        providerPlayers.get().insertPlayerProfile(KingDataTest.playerMichProfile)
        providerPlayers.get().insertPlayerProfile(KingDataTest.playerRaphProfile)
    }
}
