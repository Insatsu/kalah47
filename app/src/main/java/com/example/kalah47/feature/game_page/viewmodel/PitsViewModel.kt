package com.example.kalah47.feature.game_page.viewmodel

import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.kalah47.core.MY_TAG
import com.example.kalah47.model.KalahGameStatus
import com.example.kalah47.model.KalahModel
import com.example.kalah47.repository.KalahAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


open class PitsViewModel : ViewModel() {
    protected open val title = "PITsVIEWMODEL"

    // * Just for init value
    private var plr0Color: Color = Color.Black
    private var plr1Color: Color = Color.Black
    var tableTheme = mutableIntStateOf(0)

    private lateinit var scope: CoroutineScope
    private lateinit var snackbarHostState: SnackbarHostState

    open val kalahModel = KalahModel(gameStatus = mutableStateOf(KalahGameStatus.PROGRESS))
    val isRepeatMove = mutableStateOf(false)

    val pitsRects: MutableList<Rect> = mutableListOf()


    fun setSnackbar(scope: CoroutineScope, snackbarHostState: SnackbarHostState) {
        this.scope = scope
        this.snackbarHostState = snackbarHostState
    }

    fun setColors(plr0Color: Color, plr1Color: Color, tableTheme: Int) {
        this.plr1Color = plr1Color
        this.plr0Color = plr0Color
        this.tableTheme.intValue = tableTheme
    }

    open fun setKalahSettings(pitsCountOneSide: Int, jewelsCount: Int) {
//      * If current game state already has same counts and table state then just nothing
//      * else set settings
        if (
            (pitsCountOneSide == kalahModel.pitsCountOneSide.intValue &&
                    jewelsCount == kalahModel.jewelCountInOnePit.intValue &&
                    kalahModel.pitsState.isNotEmpty() && kalahModel.pitsState.size == pitsCountOneSide * 2 + 2
                    )
        )
            return

        Log.d(MY_TAG, "$title setKalahSettings: pits: $pitsCountOneSide, jewels: $jewelsCount")
        kalahModel.pitsCountOneSide.intValue = pitsCountOneSide
        kalahModel.jewelCountInOnePit.intValue = jewelsCount

        initConfiguration()
    }

    fun showSnackbarWithMessage(message: String) {
        scope.launch {
            snackbarHostState.showSnackbar(
                message,
            )
        }
    }

    open fun makeMoveFrom(id: Int): Boolean {
        Log.d(MY_TAG, "$title makeMoveFrom")
//      * Determine that player choose right pit else return false
        if (!(kalahModel.currentPlayer.intValue == 0 && id < kalahModel.pitsCountOneSide.intValue) &&
            !(kalahModel.currentPlayer.intValue == 1 && id > kalahModel.pitsCountOneSide.intValue)
        )
            return false



        makeMove(id)

        val result = defineWinner()
        if (result != -1) {
            kalahModel.winner.intValue = result
            return true
        }

        return true
    }

    //  * Define necessary color. Highlight color of current player
    fun getRightColor(id: Int): Color {
        return if (id <= kalahModel.pitsCountOneSide.intValue) {
            plr0Color.let {
                if (kalahModel.winner.intValue != -1)
                    when (kalahModel.winner.intValue) {
                        0 -> return@let it
                        1 -> return@let it.copy(0.5f)
                        else -> Unit
                    }
                if (kalahModel.currentPlayer.intValue != 0) it.copy(0.5f)
                else it
            }
        } else {
            plr1Color.let {
                if (kalahModel.winner.intValue != -1)
                    when (kalahModel.winner.intValue) {
                        1 -> return@let it
                        0 -> return@let it.copy(0.5f)
                        else -> Unit
                    }
                if (kalahModel.currentPlayer.intValue != 1) it.copy(0.5f)
                else it
            }
        }
    }

    protected open fun swapPlayer() {
        Log.d(MY_TAG, "$title swapPlayer: old value = ${kalahModel.currentPlayer.intValue}")
        kalahModel.currentPlayer.intValue = (kalahModel.currentPlayer.intValue + 1) % 2
    }

    private fun makeMove(id: Int) {
        Log.d(MY_TAG, "$title private makeMove")

        val lastId =
            KalahAction.makeMove(kalahModel.pitsState, id, kalahModel.currentPlayer.intValue)


        KalahAction.makeMoveWithImmutableList(
            kalahModel.pitsJewels,
            id,
            kalahModel.currentPlayer.intValue
        )


        if (lastId == KalahAction.getKalahId(
                kalahModel.pitsState.size,
                kalahModel.currentPlayer.intValue
            )
        ) {
            isRepeatMove.value = true
        } else {
            swapPlayer()
            isRepeatMove.value = false
        }


    }

    private fun defineWinner(): Int {
        Log.d(MY_TAG, "$title private defineWinner")

        return KalahAction.defineWinnerIfGameEnd(
            kalahModel.pitsState,
            kalahModel.jewelCountInOnePit.intValue * kalahModel.pitsCountOneSide.intValue * 2
        )
    }

    //  * Fill table by jewels
    private fun initConfiguration() {
        Log.d(MY_TAG, "$title initJewels")

        initPitState()
        initPitsJewels()
        initPitsPositions()

        Log.d(MY_TAG, "$title end initJewels")
    }

    protected fun initPitsPositions() {
        pitsRects.clear()
        pitsRects.addAll((1..getAllPitsCount()).map { Rect(Offset.Zero, 0f) })
    }

    private fun initPitState() {
        kalahModel.pitsState.clear()
//      * Set in each element(pit/kalah) jewels count
        for (i in 1..getAllPitsCount()) {
            if (i == kalahModel.pitsCountOneSide.intValue + 1 || i == kalahModel.pitsCountOneSide.intValue * 2 + 2) {
                kalahModel.pitsState.add(0)
                continue
            }
            kalahModel.pitsState.add(kalahModel.jewelCountInOnePit.intValue)
        }
    }

    private fun initPitsJewels() {
        kalahModel.pitsJewels.clear()
        for (i in 1..getAllPitsCount()) {
            kalahModel.pitsJewels.add(mutableStateListOf())
            if (i == kalahModel.pitsCountOneSide.intValue + 1 || i == kalahModel.pitsCountOneSide.intValue * 2 + 2) {
                continue
            }
            kalahModel.pitsJewels[i - 1]
                .addAll((i - 1) * kalahModel.jewelCountInOnePit.intValue until i * kalahModel.jewelCountInOnePit.intValue)
        }
    }

    private fun getAllPitsCount(): Int {
        return kalahModel.pitsCountOneSide.intValue * 2 + 2
    }

}