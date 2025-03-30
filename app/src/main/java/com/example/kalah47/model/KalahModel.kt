package com.example.kalah47.model

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.example.kalah47.repository.PlayersInfo


data class KalahModel(
    var gameId: MutableState<String> = mutableStateOf("-1"),
    var gameStatus: MutableState<KalahGameStatus> = mutableStateOf(KalahGameStatus.CREATED),
    val playersJoint: SnapshotStateList<PlayerModel> = mutableStateListOf(
        PlayerModel(
            PlayersInfo(),
            0
        )
    ),
    // Pits list following kind: [pit01...pit0n, kalah0, pit11...pit1n, kalah1]
    val pitsState: SnapshotStateList<Int> = mutableStateListOf(),

//  !===========
    val pitsJewels: SnapshotStateList<SnapshotStateList<Int>> = mutableStateListOf(),
//  !===========

    var pitsCountOneSide: MutableIntState = mutableIntStateOf(6),
    var jewelCountInOnePit: MutableIntState = mutableIntStateOf(6),
    val currentPlayer: MutableIntState = mutableIntStateOf(0),
    var winner: MutableIntState = mutableIntStateOf(-1)
) {
    fun copyFrom(kalahModel: KalahModel) {
        this.gameId.value = kalahModel.gameId.value
        this.gameStatus.value = kalahModel.gameStatus.value

        this.pitsState.clear()
        this.pitsState.addAll(kalahModel.pitsState)

        this.playersJoint.clear()
        this.playersJoint.addAll(kalahModel.playersJoint)

//  !===========
        this.pitsJewels.clear()
        this.pitsJewels.addAll(kalahModel.pitsJewels)
//  !===========

        this.pitsCountOneSide.intValue = kalahModel.pitsCountOneSide.intValue
        this.jewelCountInOnePit.intValue = kalahModel.jewelCountInOnePit.intValue

        this.currentPlayer.intValue = kalahModel.currentPlayer.intValue
        this.winner.intValue = kalahModel.winner.intValue
    }

    fun copyFrom(kalahModel: KalahModelFirestore) {
        this.gameId.value = kalahModel.gameId
        this.gameStatus.value = kalahModel.gameStatus

        this.pitsState.clear()
        this.pitsState.addAll(kalahModel.pitsState)

        this.playersJoint.clear()
        this.playersJoint.addAll(kalahModel.playersJoint)

//  !===========
        this.pitsJewels.clear()
        this.pitsJewels.addAll(kalahModel.pitsJewels.map { it.jewels.toMutableStateList() })
//  !===========

        this.pitsCountOneSide.intValue = kalahModel.pitsCountOneSide
        this.jewelCountInOnePit.intValue = kalahModel.jewelCountInOnePit

        this.currentPlayer.intValue = kalahModel.currentPlayer
        this.winner.intValue = kalahModel.winner
    }
}

data class PitJewels(
    val jewels: MutableList<Int> = mutableListOf()
)

data class KalahModelFirestore(
    var gameId: String = "-1",
    var gameStatus: KalahGameStatus = KalahGameStatus.CREATED,
    val pitsState: MutableList<Int> = mutableListOf(),
    val playersJoint: MutableList<PlayerModel> = mutableListOf(PlayerModel(PlayersInfo(), 0)),

//  !===========
    val pitsJewels: MutableList<PitJewels> = mutableListOf(),
//  !===========

    val pitsCountOneSide: Int = 6,
    val jewelCountInOnePit: Int = 6,
    val currentPlayer: Int = 0,
    var winner: Int = -1
) {
    companion object {
        fun fromKalahModel(kalahModel: KalahModel) =
            KalahModelFirestore(
                gameId = kalahModel.gameId.value,
                gameStatus = kalahModel.gameStatus.value,
                pitsState = kalahModel.pitsState,
                currentPlayer = kalahModel.currentPlayer.intValue,
                pitsCountOneSide = kalahModel.pitsCountOneSide.intValue,
                jewelCountInOnePit = kalahModel.jewelCountInOnePit.intValue,
                winner = kalahModel.winner.intValue,
                playersJoint = kalahModel.playersJoint,

//              !===========
                pitsJewels = kalahModel.pitsJewels.map { PitJewels(it) }.toMutableList()
//              !===========
            )
    }

    fun toKalahModel() = KalahModel(
        gameId = mutableStateOf(this.gameId),
        gameStatus = mutableStateOf(this.gameStatus),
        pitsState = this.pitsState.toMutableStateList(),
        pitsCountOneSide = mutableIntStateOf(this.pitsCountOneSide),
        jewelCountInOnePit = mutableIntStateOf(this.jewelCountInOnePit),
        currentPlayer = mutableIntStateOf(this.currentPlayer),
        winner = mutableIntStateOf(this.winner),
        playersJoint = this.playersJoint.toMutableStateList(),

//      !=========
        pitsJewels = this.pitsJewels.map { it.jewels.toMutableStateList() }.toMutableStateList(),
//      !=========

    )

}


enum class KalahGameStatus {
    CREATED,
    JOINED,
    PAUSED,
    PROGRESS,
    FINISHED
}