package com.example.kalah47.feature.game_page.widgets

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.kalah47.core.MY_TAG
import com.example.kalah47.feature.game_page.viewmodel.PitsVMBot
import com.example.kalah47.feature.game_page.viewmodel.PitsViewModel
import com.example.kalah47.model.KalahGameStatus

@Composable
fun Pit(height: Dp, width: Dp, id: Int, vm: PitsViewModel, modifier: Modifier = Modifier) {
    val value = vm.kalahModel.pitsState.getOrNull(id) ?: 0

    Box(
        modifier
            .width(width)
            .height(height)
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.2f), CircleShape)
            .border(2.dp, vm.getRightColor(id), CircleShape)
            .onGloballyPositioned {
                if (vm.pitsRects.isEmpty())
                    return@onGloballyPositioned

                vm.pitsRects[id] = it.boundsInWindow()
                if(id==1)
                    Log.d(MY_TAG, "pits poss: ${vm.pitsRects.size} -- ${vm.pitsRects}")
            }
//          * Add 'enable' property
            .clickable(
                enabled =
                vm.kalahModel.winner.intValue == -1 &&
                        value != 0 &&
                        vm.kalahModel.gameStatus.value != KalahGameStatus.CREATED
            ) {
//              * Check its pit could be tapped (if this game with bot and current move is bot's or inner logic)
                if ((vm is PitsVMBot && vm.kalahModel.currentPlayer.intValue == vm.bot.botPlayer)
                    || !vm.makeMoveFrom(id)
                )
                    vm.showSnackbarWithMessage("Не ваш ход")

                if (vm.isRepeatMove.value)
                    vm.showSnackbarWithMessage("Сделайте ещё ход")


            },
        contentAlignment = Alignment.Center
    )
    {
        val textValue = vm.kalahModel.pitsJewels.getOrNull(id)?.size ?: 0

        Text(
            "$textValue",
            modifier = Modifier.align(Alignment.Center),
            color = Color.Black,
            fontSize = TextUnit(20f, TextUnitType.Sp)
        )

    }
}
