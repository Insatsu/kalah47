package com.example.kalah47.feature.online_page.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.kalah47.R
import com.example.kalah47.feature.common_components.getPlayersInfo
import com.example.kalah47.repository.CommonActions
import com.example.kalah47.repository.DataStoreManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.compose.koinInject


@Composable
fun UserSettings(
    dataStore: DataStoreManager = koinInject(),
    width: Float = 0.7f
) {
    val coroutine = rememberCoroutineScope()
    val showBottomSheet = remember { mutableStateOf(false) }

    val q = getPlayersInfo()
    val playerInfo = remember {
        q
    }

    val context = LocalContext.current
    val imageId = remember {
        mutableIntStateOf(
            CommonActions.getRIdFromString(context, -1)
        )
    }

    val imageSize = 250.dp
//  * Listen any changes playerInfo and change imageId with this
    LaunchedEffect(0) {
        snapshotFlow { playerInfo.value }.onEach {
            imageId.intValue = CommonActions.getRIdFromString(context, playerInfo.value.iconsId)
        }.launchIn(coroutine)
    }

//  * Body
    Column(
        Modifier.fillMaxWidth(width),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//      * Image part
        Box(
            Modifier
                .wrapContentSize()
                .clickable(
                    onClick = {
                        showBottomSheet.value = true
                    },
//                  * Indication on tap has image's form
                    indication = rememberRipple(
                        bounded = true,
                        radius = imageSize/2 - 1.dp,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    interactionSource = remember { MutableInteractionSource() }
                )
        ) {
//          * Image itself
            Image(
                modifier = Modifier
                    .size(imageSize)
                    .clip(CircleShape)
                    .border(1.dp, MaterialTheme.colorScheme.onBackground, CircleShape),
                painter = painterResource(
                    id = imageId.intValue
                ),
                contentDescription = "ava",
                contentScale = ContentScale.Crop
            )
//          * Icon pen
            Surface(
                Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = (-15).dp)
                    .wrapContentSize(),
                shape = RoundedCornerShape(7.dp),
                shadowElevation = 5.dp
            ) {
                Icon(
                    modifier = Modifier
                        .size(45.dp)
                        .clip(RoundedCornerShape(7.dp))
                        .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.9f))
                        .padding(5.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.pencil_outline_icon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

        }

//      * Nickname part
        SetNickname(
            dataStore = dataStore,
            playerInfo = playerInfo.value
        )

//      * BottomSheet part
        if (showBottomSheet.value)
            ChoiceAveBottomSheet(
                dataStore = dataStore,
                playersInfo = playerInfo.value, showBottomSheet = showBottomSheet,
                context = context
            )

    }
}
