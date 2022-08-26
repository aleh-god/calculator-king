package by.godevelopment.kingcalculator.data.utils

import by.godevelopment.kingcalculator.data.entities.GameNote
import by.godevelopment.kingcalculator.data.entities.PartyNote
import by.godevelopment.kingcalculator.data.entities.PlayerProfile
import by.godevelopment.kingcalculator.data.entities.TricksNote
import by.godevelopment.kingcalculator.domain.gamesdomain.models.GameModel
import by.godevelopment.kingcalculator.domain.gamesdomain.models.TricksNoteModel
import by.godevelopment.kingcalculator.domain.playersdomain.models.PartyModel
import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerModel

fun PartyNote.toPartyModel(): PartyModel =
    PartyModel(
        id = id,
        partyName = partyName,
        startedAt = startedAt,
        partyLastTime = partyLastTime,
        playerOneId = playerOneId,
        playerTwoId = playerTwoId,
        playerThreeId = playerThreeId,
        playerFourId = playerFourId
    )

fun PlayerModel.toPlayerProfile(): PlayerProfile =
    PlayerProfile(
        id = id,
        email = email,
        name = name,
        isActive = isActive,
        color = color,
        avatar = avatar
    )

fun PlayerProfile.toPlayerModel(): PlayerModel =
    PlayerModel(
        id = id,
        email = email,
        name = name,
        isActive = isActive,
        avatar = avatar,
        color = color
    )

fun TricksNoteModel.toTricksNote(): TricksNote =
    TricksNote(
        id = id,
        gameId = gameId,
        playerId = playerId,
        gameType = gameType,
        tricksCount = tricksCount
    )

fun TricksNote.toTricksNoteModel(): TricksNoteModel =
    TricksNoteModel(
        id = id,
        gameId = gameId,
        playerId = playerId,
        gameType = gameType,
        tricksCount = tricksCount
    )

fun GameNote.toGameModel(): GameModel =
    GameModel(
        id = id,
        partyId = partyId,
        gameType = gameType,
        finishedAt = finishedAt
    )
