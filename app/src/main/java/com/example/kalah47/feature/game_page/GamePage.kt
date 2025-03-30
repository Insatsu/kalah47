package com.example.kalah47.feature.game_page

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kalah47.R
import com.example.kalah47.core.TABLE_THEMES_COUNT
import com.example.kalah47.core.TABLE_THEMES_NAME
import com.example.kalah47.core.TABLE_THEME_DEFAULT
import com.example.kalah47.feature.common_components.getSettingsData
import com.example.kalah47.feature.game_page.viewmodel.PitsVMBot
import com.example.kalah47.feature.game_page.viewmodel.PitsVMOnline
import com.example.kalah47.feature.game_page.viewmodel.PitsViewModel
import com.example.kalah47.feature.game_page.widgets.GameTable
import com.example.kalah47.model.KalahGameStatus
import com.example.kalah47.repository.CommonActions
import kotlinx.coroutines.delay

@Composable
fun GamePage(
    pitVM: PitsViewModel = viewModel(),
    colorScheme: ColorScheme = MaterialTheme.colorScheme
) {
//  * Table color from dataStore
    MyInit(pitVM, colorScheme)

    val snackbarHostState = setSnackbar(pitVM)
    val context = LocalContext.current

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                )
//              * Set snackbar duration to 1 sec
                LaunchedEffect(key1 = data) {
                    delay(timeMillis = 1000)
                    data.dismiss()
                }

            }
        }
    ) { paddingValue ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(
                        id = CommonActions.getRIdFromString(
                            context,
                            pitVM.tableTheme.intValue,
                            imageName = TABLE_THEMES_NAME,
                            imageCount = TABLE_THEMES_COUNT,
                            imageDefault = TABLE_THEME_DEFAULT
                        )
                    ),
                    contentScale = ContentScale.Crop
                )
                .padding(paddingValue),
            contentAlignment = Alignment.Center
        ) {
            val contraits = this

            GameTable(pitVM)
            GameInfo(pitVM)

        }
    }
}

@Composable
private fun MyInit(
    pitVM: PitsViewModel,
    colorScheme: ColorScheme
) {
    val q = getSettingsData()
    val settings = remember {
        q
    }
    val botName = stringResource(id = R.string.bot_name)

    pitVM.setColors(
        colorScheme.primary,
        colorScheme.tertiary,
        settings.value.tableTheme
    )

    when (pitVM) {
        is PitsVMOnline -> pitVM.fetchGameModel()
        is PitsVMBot -> {
            pitVM.setKalahSettings(
                settings.value.pitsCountOneSide,
                settings.value.jewelCountInOnePit
            )
            pitVM.setBot(
                botId = settings.value.botId,
                botLevel = settings.value.botLevel,
                botName = botName
            )
            if (pitVM.bot.botPlayer == 0 && settings.value.jewelCountInOnePit != 0)
                pitVM.makeFirstMove()
        }

        else -> pitVM.setKalahSettings(
            settings.value.pitsCountOneSide,
            settings.value.jewelCountInOnePit
        )
    }


}


@Composable
private fun GameInfo(pitVM: PitsViewModel) {
    if (pitVM.kalahModel.winner.intValue != -1)
        WinnerInfo(pitVM)
    else if (pitVM is PitsVMOnline && (pitVM.kalahModel.gameStatus.value == KalahGameStatus.CREATED || pitVM.kalahModel.gameStatus.value == KalahGameStatus.PAUSED))
        OnlineGameInfo(pitVM = pitVM)
    else
        MoveInfo(pitVM)
}


@Composable
private fun WinnerInfo(pitVM: PitsViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .fillMaxHeight(0.2f)
            .clip(RoundedCornerShape(25.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer.copy(0.7f))
    ) {
        WinnersName(pitVM = pitVM, modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
private fun MoveInfo(pitVM: PitsViewModel) {
    Box(
        Modifier
            .fillMaxWidth(0.3f)
    ) {
        MovesName(pitVM = pitVM, modifier = Modifier.align(Alignment.Center))
    }
}


@Composable
private fun WinnersName(pitVM: PitsViewModel, modifier: Modifier) {
    if (pitVM.kalahModel.winner.intValue == 2)
    {
        Text(
            stringResource(
                id = R.string.game_info_draw,
            ),
            modifier = modifier,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontSize = TextUnit(24f, TextUnitType.Sp)
        )
        return
    }


    if (pitVM is PitsVMBot) {
        if (pitVM.kalahModel.winner.intValue == pitVM.bot.botPlayer)
            Text(
                stringResource(
                    id = R.string.game_info_winner,
                    stringResource(id = R.string.bot_name)
                ),
                modifier = modifier,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = TextUnit(24f, TextUnitType.Sp)
            )
        else
            Text(
                stringResource(
                    id = R.string.game_info_winner,
                    stringResource(id = R.string.game_info_winner_player)
                ),
                modifier = modifier,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = TextUnit(24f, TextUnitType.Sp)
            )
    } else if (pitVM is PitsVMOnline) {
        Text(
            stringResource(
                id = R.string.game_info_winner,
                pitVM.kalahModel.playersJoint.toList()
                    .singleOrNull { it.id == pitVM.kalahModel.currentPlayer.intValue }?.info?.nickName
                    ?: ""
            ),
            modifier = modifier,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontSize = TextUnit(24f, TextUnitType.Sp)
        )
    } else {
        Text(
            stringResource(
                id = R.string.game_info_winner,
                "${pitVM.kalahModel.currentPlayer.intValue + 1} ${stringResource(id = R.string.game_info_winner_player)}"
            ), modifier = modifier,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontSize = TextUnit(24f, TextUnitType.Sp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun MovesName(pitVM: PitsViewModel, modifier: Modifier) {
    if (pitVM is PitsVMBot) {
        if (pitVM.kalahModel.currentPlayer.intValue == pitVM.bot.botPlayer)
            Text(
                stringResource(
                    id = R.string.game_info_move,
                    stringResource(id = R.string.bot_name)
                ),
                modifier = modifier,
                color = Color.Black,
                fontSize = TextUnit(20f, TextUnitType.Sp)
            )
        else
            Text(
                stringResource(
                    id = R.string.game_info_move,
                    stringResource(id = R.string.game_info_move_player)
                ),
                modifier = modifier,
                color = Color.Black,
                fontSize = TextUnit(20f, TextUnitType.Sp)

            )
    } else if (pitVM is PitsVMOnline) {
        Text(
            stringResource(
                id = R.string.game_info_move,
                pitVM.kalahModel.playersJoint.toList()
                    .singleOrNull { it.id == pitVM.kalahModel.currentPlayer.intValue }?.info?.nickName
                    ?: ""
            ),
            modifier = modifier,

            color = Color.Black,
            fontSize = TextUnit(20f, TextUnitType.Sp)
        )
    } else {
        Text(
            stringResource(
                id = R.string.game_info_move,
                "${pitVM.kalahModel.currentPlayer.intValue + 1} ${stringResource(id = R.string.game_info_move_player)}"
            ),
            modifier = modifier,

            color = Color.Black,
            fontSize = TextUnit(20f, TextUnitType.Sp)
        )
    }
}

@Composable
private fun OnlineGameInfo(pitVM: PitsViewModel) {
    val orientation = LocalConfiguration.current.orientation

    val isInfoShow = rememberSaveable {
        mutableIntStateOf(1)
    }

    val maxWidth = remember {
        mutableFloatStateOf(1f)
    }

    val maxHeight = remember {
        mutableFloatStateOf(1f)
    }

    when (orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            maxWidth.floatValue = 0.85f
            maxHeight.floatValue = 0.3f
        }

        else -> {
            maxWidth.floatValue = 0.5f
            maxHeight.floatValue = 0.65f
        }
    }


    if (isInfoShow.intValue == 1)
        Box(
            modifier = Modifier
                .fillMaxWidth(maxWidth.floatValue)
                .fillMaxHeight(maxHeight.floatValue)
                .clip(RoundedCornerShape(25.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer.copy(0.7f))
        ) {
            OnlineGameString(pitVM = pitVM, modifier = Modifier.align(Alignment.Center))
        }

}


@Composable
private fun OnlineGameString(pitVM: PitsViewModel, modifier: Modifier) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "${stringResource(id = R.string.online_join_textfield_placeholder)}: ${pitVM.kalahModel.gameId.value}",
            modifier = modifier,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            fontSize = TextUnit(24f, TextUnitType.Sp)
        )

        if (pitVM.kalahModel.gameStatus.value == KalahGameStatus.CREATED)
            Text(
                stringResource(id = R.string.game_info_waiting_second_player),
                modifier = modifier,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontSize = TextUnit(24f, TextUnitType.Sp)
            )
        if (pitVM.kalahModel.gameStatus.value == KalahGameStatus.PAUSED)
            Text(
                stringResource(id = R.string.game_info_second_player_left),
                modifier = modifier,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontSize = TextUnit(24f, TextUnitType.Sp)
            )
    }
}


@Composable
private fun setSnackbar(pitVM: PitsViewModel): SnackbarHostState {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    pitVM.setSnackbar(scope, snackbarHostState)

    return snackbarHostState
}
