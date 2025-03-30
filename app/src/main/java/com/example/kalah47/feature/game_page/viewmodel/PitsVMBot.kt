package com.example.kalah47.feature.game_page.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.kalah47.R
import com.example.kalah47.core.MY_TAG
import com.example.kalah47.model.PlayerModel
import com.example.kalah47.repository.Bot
import com.example.kalah47.repository.PlayersInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.time.Timer
import kotlin.time.DurationUnit

class PitsVMBot : PitsViewModel() {
    override val title: String = "PitsVMBOT"
    private val _bot = Bot()
    val bot
        get() = _bot

    fun makeFirstMove() {
        Log.d(MY_TAG, "$title makeFirstMove ")
        viewModelScope.launch(Dispatchers.Default) {
            delay(1600)
            do {
                makeMoveFrom(bot.botMove(kalahModel))
            } while (isRepeatMove.value)
        }
    }

    fun setBot(botId: Int, botLevel: Int, botName: String) {
        this.bot.apply {
            botPlayer = botId
            allMoveCan = botLevel
            botInfo = PlayerModel(
                info = PlayersInfo(nickName = botName, iconsId = R.drawable.bot),
                id = botId
            )
        }

        if (kalahModel.playersJoint.size == 1)
            kalahModel.playersJoint.add(this.bot.botInfo)
        else
            kalahModel.playersJoint[1] = this.bot.botInfo

        kalahModel.playersJoint[0].id = (1 + botId) % 2

    }

    override fun swapPlayer() {
        super.swapPlayer()
        if (kalahModel.currentPlayer.intValue == bot.botPlayer)
            viewModelScope.launch(Dispatchers.Default) {
                do {
//                  * Some delay for imitation "thought"
                    val timeStart = Timer.start()
                    val botMove = bot.botMove(kalahModel)
                    val timeEnd = timeStart.end
                    if (timeEnd.toDouble(DurationUnit.SECONDS) < 1) {
                        Log.d(MY_TAG, "Solve time: ${timeEnd.toDouble(DurationUnit.SECONDS)}")
                        delay(600)
                    }

                    makeMoveFrom(botMove)

                } while (isRepeatMove.value)
            }
    }

}