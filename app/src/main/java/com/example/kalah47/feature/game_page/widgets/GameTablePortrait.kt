package com.example.kalah47.feature.game_page.widgets

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.kalah47.core.MY_TAG
import com.example.kalah47.feature.game_page.viewmodel.PitsViewModel


@Composable
internal fun GameTablePortrait(
    pitVM: PitsViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 5.dp)
    ) {
        ArrowsBySidePortrait()
    }

    BoxWithConstraints(
        Modifier.padding(horizontal = 10.dp, vertical = 10.dp), contentAlignment = Alignment.Center
    ) {
        val constraints = this
        val longerSide: Dp = constraints.maxHeight
        val smallerSide: Dp = constraints.maxWidth

        val tableWidth = smallerSide * 0.9f
        val pitHeight = (longerSide / (pitVM.kalahModel.pitsCountOneSide.intValue + 2)) * 0.9f
        val spaceForPits = longerSide - pitHeight * 2

        val pitWidth: Dp = getPitLength(tableWidth)

        Game(tableWidth, pitHeight, pitVM, spaceForPits, pitWidth)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Jewels(vm = pitVM)
    }
}

@Composable
private fun Game(
    tableWidth: Dp,
    pitHeight: Dp,
    pitVM: PitsViewModel,
    spaceForPits: Dp,
    pitWidth: Dp,
) {
    val modifier = Modifier.rotate(rotateIfNeed(pitVM))
    Log.d(MY_TAG, "Rotate? : ${rotateIfNeed(pitVM)}")

    Column(
        Modifier
            .width(tableWidth)
            .fillMaxHeight()
            .rotate(rotateIfNeed(pitVM))
    ) {
        KalahPit(
            kalahWidth = tableWidth,
            kalahHeight = pitHeight,
            id = pitVM.kalahModel.pitsCountOneSide.intValue * 2 + 1,
            vm = pitVM,
            modifierWithRotate = modifier
        )
        Row(
            Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
//          * Left side
            Column(
                Modifier
                    .padding(start = pitPadding)
                    .height(spaceForPits),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                for (i in 0..<pitVM.kalahModel.pitsCountOneSide.intValue) {
                    Pit(
                        width = pitWidth,
                        height = pitHeight,
                        id = i,
                        vm = pitVM,
                        modifier = modifier
                    )
                }
            }


//          * Right side
            Column(
                Modifier
                    .padding(end = pitPadding)
                    .height(spaceForPits),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                for (i in pitVM.kalahModel.pitsCountOneSide.intValue * 2 downTo pitVM.kalahModel.pitsCountOneSide.intValue + 1) {
                    Pit(
                        width = pitWidth,
                        height = pitHeight,
                        id = i,
                        vm = pitVM,
                        modifier = modifier
                    )
                }
            }
        }
        KalahPit(
            kalahWidth = tableWidth,
            kalahHeight = pitHeight,
            id = pitVM.kalahModel.pitsCountOneSide.intValue,
            vm = pitVM,
            modifierWithRotate = modifier
        )
    }
}


