package by.godevelopment.kingcalculator.domain.partiesdomain.usecases

import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.commons.utils.mapResult
import by.godevelopment.kingcalculator.domain.partiesdomain.models.GamesTableItemModel
import by.godevelopment.kingcalculator.domain.partiesdomain.repositories.PartyRepository
import javax.inject.Inject

class GetGamesByPartyIdUseCase @Inject constructor(
    private val partyRepository: PartyRepository,
    private val mapGamesNotesListToItemModelListUseCase: MapGamesNotesListToItemModelListUseCase
) {

    suspend operator fun invoke(partyId: Long): ResultDataBase<List<GamesTableItemModel>> {
        return partyRepository
            .getAllGamesNotesByPartyId(partyId)
            .mapResult { mapGamesNotesListToItemModelListUseCase(it) }
    }
}
