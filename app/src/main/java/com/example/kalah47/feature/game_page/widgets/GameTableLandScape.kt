package com.example.kalah47.feature.game_page.widgets

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
import com.example.kalah47.feature.game_page.viewmodel.PitsViewModel


@Composable
internal fun LandScapeGameTable(
    pitVM: PitsViewModel,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 5.dp)
    ) {
        ArrowsBySideLandScape()
    }

    BoxWithConstraints(
        Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        val constraints = this
        val longerSide: Dp = constraints.maxWidth
        val smallerSide: Dp = constraints.maxHeight

        val tableHeight = smallerSide * 0.9f
        val pitWidth = (longerSide / (pitVM.kalahModel.pitsCountOneSide.intValue + 2)) * 0.9f
        val spaceForPits = longerSide - pitWidth * 2

        val pitHeight: Dp = getPitLength(tableHeight)

        Game(tableHeight, pitWidth, pitVM, spaceForPits, pitHeight)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Jewels(vm = pitVM)
    }
}

@Composable
private fun Game(
    tableHeight: Dp,
    pitWidth: Dp,
    pitVM: PitsViewModel,
    spaceForPits: Dp,
    pitHeight: Dp
) {
    val modifier = Modifier.rotate(rotateIfNeed(pitVM))

    Row(
        Modifier
            .height(tableHeight)
            .fillMaxWidth()
            .rotate(rotateIfNeed(pitVM))
    ) {
        KalahPit(
            kalahWidth = pitWidth,
            kalahHeight = tableHeight,
            id = pitVM.kalahModel.pitsCountOneSide.intValue * 2 + 1,
            vm = pitVM,
            modifierWithRotate = modifier
        )
        Column(
            Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
//          * Top side
            Row(
                Modifier
                    .padding(top = pitPadding)
                    .width(spaceForPits),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                for (i in pitVM.kalahModel.pitsCountOneSide.intValue * 2 downTo pitVM.kalahModel.pitsCountOneSide.intValue + 1) {
                    Pit(
                        width = pitWidth, height = pitHeight, id = i, vm = pitVM,
                        modifier = modifier
                    )
                }
            }

//          * Bottom side
            Row(
                Modifier
                    .padding(bottom = pitPadding)
                    .width(spaceForPits),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                for (i in 0..<pitVM.kalahModel.pitsCountOneSide.intValue) {
                    Pit(
                        width = pitWidth, height = pitHeight, id = i, vm = pitVM,
                        modifier = modifier
                    )
                }
            }

        }
        KalahPit(
            kalahWidth = pitWidth,
            kalahHeight = tableHeight,
            id = pitVM.kalahModel.pitsCountOneSide.intValue,
            vm = pitVM,
            modifierWithRotate = modifier
        )
    }
}


