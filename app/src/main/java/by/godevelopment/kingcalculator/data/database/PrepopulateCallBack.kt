package by.godevelopment.kingcalculator.data.database

import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import by.godevelopment.kingcalculator.commons.KingDataTest
import by.godevelopment.kingcalculator.commons.TAG
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
        Log.i(TAG, "PrepopulateCallBack addTricks: ")
        KingDataTest.tricks.forEach {
            val r = providerTricks.get().insertTricksNote(it)
            Log.i(TAG, "PrepopulateCallBack addTricks: $r = $it")
        }
    }

    private suspend fun addGames() {
        Log.i(TAG, "PrepopulateCallBack addGames: ")
        KingDataTest.games.forEach {
            val r = providerGames.get().insertGamesNote(it)
            Log.i(TAG, "PrepopulateCallBack addGames: it = $it r = $r")
        }
    }

    private suspend fun addParty() {
        Log.i(TAG, "PrepopulateCallBack addParty: ")
        val r = providerParties.get().insertPartyNote(KingDataTest.party)
        Log.i(TAG, "PrepopulateCallBack addParty: $r")
    }

    private suspend fun addPlayers() {
        Log.i(TAG, "PrepopulateCallBack addPlayers: ")
        val p1 = providerPlayers.get().insertPlayerProfile(KingDataTest.playerDonProfile)
        val p2 = providerPlayers.get().insertPlayerProfile(KingDataTest.playerLeoProfile)
        val p3 = providerPlayers.get().insertPlayerProfile(KingDataTest.playerMichProfile)
        val p4 = providerPlayers.get().insertPlayerProfile(KingDataTest.playerRaphProfile)
        Log.i(TAG, "PrepopulateCallBack addPlayers: $p1, $p2, $p3, $p4")
    }
}
