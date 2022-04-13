package by.godevelopment.kingcalculator.data.repositories

import android.util.Log
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.data.SourceTestData
import by.godevelopment.kingcalculator.domain.models.ItemPlayerModel
import by.godevelopment.kingcalculator.domain.models.PlayerDataModel
import by.godevelopment.kingcalculator.domain.models.PlayerModel
import by.godevelopment.kingcalculator.domain.repositories.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlayerRepositoriesImpl @Inject constructor(

) : PlayerRepository {
    override suspend fun getPlayerById(): PlayerModel {
        TODO("Not yet implemented")
    }

    override fun getPlayers(): Flow<List<ItemPlayerModel>> = flow {
        emit(SourceTestData.listPlayers)
    }

    override suspend fun saveNewPlayer(params: PlayerDataModel): Boolean {
        Log.i(TAG, "PlayerRepositoriesImpl saveNewPlayer: $params")
        return true
    }
}