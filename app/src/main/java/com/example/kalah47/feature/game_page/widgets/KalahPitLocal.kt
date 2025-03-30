package com.example.kalah47.feature.game_page.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.kalah47.feature.game_page.viewmodel.PitsViewModel
import kotlin.math.round

@Composable
fun KalahPitLocal(
    kalahWidth: Dp,
    kalahHeight: Dp,
    id: Int,
    vm: PitsViewModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .width(kalahWidth)
            .height(kalahHeight)
            .onGloballyPositioned {
                if (vm.pitsRects.isEmpty())
                    return@onGloballyPositioned

                vm.pitsRects[id] = it.boundsInWindow()
            }
            .background(Color.Black.copy(alpha = 0.2f), CircleShape)
            .border(2.dp, vm.getRightColor(id), CircleShape),
        contentAlignment = Alignment.Center
    ) {


        if ((vm.kalahModel.currentPlayer.intValue == 0 && id == vm.kalahModel.pitsCountOneSide.intValue) ||
            (vm.kalahModel.currentPlayer.intValue == 1 && id == vm.kalahModel.pitsCountOneSide.intValue * 2 + 1)
        )
            Box(
                modifier
                    .offset {
                        val scale = 1.7
                        when (id) {
                            vm.kalahModel.pitsCountOneSide.intValue -> {
                                if (kalahHeight < kalahWidth)
                                    IntOffset(0, - round(kalahHeight.value.toInt() * scale).toInt())
                                else
                                    IntOffset(- round(kalahWidth.value.toInt() * scale).toInt(), 0)
                            }

                            else -> {
                                if (kalahHeight < kalahWidth)
                                    IntOffset(0, round(kalahHeight.value.toInt() * scale).toInt())
                                else
                                    IntOffset(round(kalahWidth.value.toInt() * scale).toInt(), 0)
                            }
                        }
                    }
                    .wrapContentHeight()
                    .width(kalahWidth)
                    .padding(vertical = 5.dp)

            ) {
//      * Star current player
                Box(
                    Modifier
                        .size(25.dp)
                        .clip(
                            CurrentPlayerIndicator()
                        )
                        .align(Alignment.Center)
                        .border(2.dp, MaterialTheme.colorScheme.error, CurrentPlayerIndicator())

                )

            }



        val textValue = vm.kalahModel.pitsJewels.getOrNull(id)?.size ?: 0
        Text(
            textValue.toString(),
            color = Color.Black,
            fontSize = TextUnit(20f, TextUnitType.Sp)
        )
        
    }
}