package com.example.kalah47.repository

import android.util.Log
import com.example.kalah47.core.MY_TAG
import com.example.kalah47.model.KalahGameStatus
import com.example.kalah47.model.KalahModel
import com.example.kalah47.model.PlayerModel

class Bot {
    lateinit var botInfo: PlayerModel

    var botPlayer: Int = -1
        set(value) {
            field = if (value >= 1) value
            else 0
        }
    var allMoveCan: Int = 0
        set(value) {
            field = if (value < 0) 0 else value
        }

    private fun score(kalahModel: KalahModel): Int {
        Log.d(MY_TAG, "BOT: score: allMove = ${allMoveCan}")

        val pits = kalahModel.pitsState.size
        var nowScore = kalahModel.pitsState[pits - 1] - kalahModel.pitsState[(pits / 2) - 1]
        if (botPlayer == 0) {
            nowScore = -nowScore
        }
        return nowScore
    }

    private fun swapPlayer(kalahModel: KalahModel) {
        kalahModel.currentPlayer.intValue = (kalahModel.currentPlayer.intValue + 1) % 2
    }

    private fun moveKalahModel(id: Int, kalahModel: KalahModel) {
        val lastId =
            KalahAction.makeMove(kalahModel.pitsState, id, kalahModel.currentPlayer.intValue)

        if (lastId != KalahAction.getKalahId(
                kalahModel.pitsState.size, kalahModel.currentPlayer.intValue
            )
        ) {
            swapPlayer(kalahModel)
        }
        setFinishedStatusIfFinish(kalahModel)

    }

    private fun setFinishedStatusIfFinish(kalahModel: KalahModel) {
        if (KalahAction.defineWinnerIfGameEnd(
                kalahModel.pitsState,
                kalahModel.jewelCountInOnePit.intValue * kalahModel.pitsCountOneSide.intValue * 2
            ) != -1
        )
            kalahModel.gameStatus.value = KalahGameStatus.FINISHED
    }


    private fun minimaxBad(id: Int, allMoveCan1: Int, kl: KalahModel): Int {
        val kalahModel = KalahModel().apply {
            copyFrom(kl)
        }
        moveKalahModel(id, kalahModel)
        if (kalahModel.gameStatus.value == KalahGameStatus.FINISHED || allMoveCan1 <= 0)
            return score(kalahModel)

        val pitsSize = kalahModel.pitsState.size
        val startVal = intArrayOf(0, pitsSize / 2)
        val nowPlayer = kalahModel.currentPlayer.intValue
        var minimaxValuecurRes = startVal[nowPlayer];

        var minimaxValue: Int = if (nowPlayer == botPlayer)
            -1000
        else 1000

        for (i in 1..<pitsSize / 2 - 1) {
            val checkPit = startVal[nowPlayer] + i
            val pit = kalahModel.pitsState[checkPit]
            if (pit != 0) {
                val cur = minimaxBad(checkPit, 0, kalahModel)
                if (minimaxValue <= cur && nowPlayer == botPlayer) {
                    minimaxValuecurRes = checkPit
                } else if (minimaxValue >= cur && nowPlayer != botPlayer) {
                    minimaxValuecurRes = checkPit
                }
            }
        }

        minimaxValue = minimaxBad(minimaxValuecurRes, allMoveCan1 - 1, kalahModel)

        return minimaxValue
    }

    fun botMove(kl: KalahModel): Int {
        val kalahModel = KalahModel().apply {
            copyFrom(kl)
        }
        val pits = kalahModel.pitsState.size
        val startVal = intArrayOf(0, pits / 2)
        var curRes = startVal[botPlayer]
        var max = -1000

        for (i in 0..<pits / 2 - 1) {
            val checkPit = startVal[botPlayer] + i
            val pit = kalahModel.pitsState[checkPit]

            if (pit != 0) {
                val cur = minimaxBad(checkPit, allMoveCan, kalahModel)
                if (max <= cur) {
                    max = cur
                    curRes = checkPit
                }
            }
        }
        Log.d(MY_TAG, "BOT: botMove: result = $curRes")
        if (max == -1000) {
            Log.d(MY_TAG, "BOT: botMove Err")
        }
        return curRes
    }
}