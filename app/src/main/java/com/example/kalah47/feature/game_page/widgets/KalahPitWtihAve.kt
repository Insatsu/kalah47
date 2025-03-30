package com.example.kalah47.feature.game_page.widgets

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.example.kalah47.R
import com.example.kalah47.feature.common_components.ResizedText
import com.example.kalah47.feature.game_page.viewmodel.PitsViewModel
import com.example.kalah47.model.PlayerModel
import com.example.kalah47.repository.CommonActions

@Composable
fun KalahPitWithAve(
    componentWidth: Dp,
    componentHeight: Dp,
    id: Int,
    vm: PitsViewModel,
    modifier: Modifier = Modifier
) {

    val playerLength = max(componentWidth, componentHeight) * 0.25f
    val kalahLength = max(componentWidth, componentHeight) - playerLength


    val player =
        (if (id == vm.kalahModel.pitsCountOneSide.intValue) vm.kalahModel.playersJoint.firstOrNull { it.id == 0 }
        else vm.kalahModel.playersJoint.firstOrNull { it.id == 1 })
            ?: PlayerModel()


    val orientation = LocalConfiguration.current.orientation

    when (orientation) {
        Configuration.ORIENTATION_PORTRAIT ->
            PortraitKalah(
                modifier,
                componentWidth,
                componentHeight,
                vm,
                id,
                playerLength,
                player,
                kalahLength
            )

        else -> {
            LandScapeKalah(
                modifier,
                componentWidth,
                componentHeight,
                vm,
                id,
                playerLength,
                player,
                kalahLength
            )

        }
    }


}

@Composable
private fun PortraitKalah(
    modifier: Modifier,
    componentWidth: Dp,
    componentHeight: Dp,
    vm: PitsViewModel,
    id: Int,
    playerWidth: Dp,
    player: PlayerModel,
    kalahWidth: Dp
) {
    Row(
        modifier = Modifier
            .width(componentWidth)
            .height(componentHeight)
    ) {
        if (id == vm.kalahModel.pitsCountOneSide.intValue * 2 + 1)
            PlayerPart(
                width = playerWidth,
                height = componentHeight,
                player = player,
                modifier = modifier
            )

        KalahPitLocal(
            kalahWidth = kalahWidth,
            kalahHeight = componentHeight,
            id = id,
            vm = vm,
//          * Some padding from players icon and nickname
            modifier = modifier.padding(horizontal = 3.dp)
        )

        if (id == vm.kalahModel.pitsCountOneSide.intValue)
            PlayerPart(
                width = playerWidth,
                height = componentHeight,
                player = player,
                modifier = modifier
            )
    }
}

@Composable
private fun LandScapeKalah(
    modifier: Modifier,
    componentWidth: Dp,
    componentHeight: Dp,
//    vm: PitsVMOnline,
    vm: PitsViewModel,
    id: Int,
    playerHeight: Dp,
    player: PlayerModel,
    kalahHeight: Dp
) {
    Column(
        modifier = Modifier
            .width(componentWidth)
            .height(componentHeight)
    ) {
//        if (player.id == -1 || (player.id == 1 && id == vm.kalahModel.pitsCountOneSide.intValue * 2 + 1))
        if (id == vm.kalahModel.pitsCountOneSide.intValue * 2 + 1)
            PlayerPart(
                width = componentWidth,
                height = playerHeight,
                player = player,
                modifier = modifier
            )

        KalahPitLocal(
            kalahWidth = componentWidth,
            kalahHeight = kalahHeight,
            id = id,
            vm = vm,
//          * Some padding from players icon and nickname
            modifier = modifier.padding(vertical = 3.dp)
        )

//        if (player.id == -1 || (player.id == 0 && id == vm.kalahModel.pitsCountOneSide.intValue))
        if (id == vm.kalahModel.pitsCountOneSide.intValue)
            PlayerPart(
                width = componentWidth,
                height = playerHeight,
                player = player,
                modifier = modifier
            )
    }
}


@Composable
fun PlayerPart(
    width: Dp,
    height: Dp,
    player: PlayerModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val imageId = remember {
        mutableIntStateOf(
            CommonActions.getRIdFromString(context, -1)
        )
    }
    LaunchedEffect(player) {
        if (player.info.nickName == context.getString(R.string.bot_name)) {
            imageId.intValue = player.info.iconsId
        } else {
            imageId.intValue = CommonActions.getRIdFromString(context, player.info.iconsId)
        }
    }

    Column(
        modifier
            .width(width)
            .height(height),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
//      * Image part
        Image(
            modifier = Modifier
                .size(height * (0.75f))
                .clip(CircleShape),
            painter = painterResource(
                id = imageId.intValue
            ),
//          * If second player not join yet show placeholder with onBackgroundColor
            colorFilter = if (imageId.intValue == CommonActions.getRIdFromString(
                    context,
                    -1
                )
            ) ColorFilter.tint(MaterialTheme.colorScheme.tertiary) else null,
            contentDescription = "ava",
            contentScale = ContentScale.Crop
        )
//      * Nick part
        ResizedText(
            text = player.info.nickName,
            modifier = Modifier.height(height * 0.25f),
            color = Color.Black
        )
//        Text(
//            player.info.nickName,
//            modifier = Modifier.height(height * 0.25f),
//            softWrap = false,
//            style = resizedTextStyle,
//            onTextLayout = { result ->
//                if (result.didOverflowWidth) {
//                    resizedTextStyle =
//                        resizedTextStyle.copy(fontSize = resizedTextStyle.fontSize * 0.85)
//                } else if (result.didOverflowHeight) {
//                    resizedTextStyle =
//                        resizedTextStyle.copy(fontSize = resizedTextStyle.fontSize * 0.95)
//                }
//            })

    }
}