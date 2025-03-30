package com.example.kalah47.feature.online_page.widgets

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.kalah47.core.MY_TAG
import com.example.kalah47.core.PLAYERS_ICONS_COUNT
import com.example.kalah47.repository.CommonActions
import com.example.kalah47.repository.DataStoreManager
import com.example.kalah47.repository.PlayersInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// * BottomSheet
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChoiceAveBottomSheet(
    dataStore: DataStoreManager,
    playersInfo: PlayersInfo,
    showBottomSheet: MutableState<Boolean>,
    context: Context
) {
    val sheetState = rememberModalBottomSheetState()
    val coroutine = rememberCoroutineScope()

    ModalBottomSheet(
        modifier = Modifier.fillMaxHeight(0.7f),
        onDismissRequest = {
            showBottomSheet.value = false
        },
        sheetState = sheetState,

        ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(5.dp),

            ) {

            items(PLAYERS_ICONS_COUNT) {
                val i = it + 1
                Log.d(MY_TAG, "ID = $i")

                val imageId = remember {
                    mutableIntStateOf(CommonActions.getRIdFromString(context, -1))
                }

                LaunchedEffect(i) {
                    imageId.intValue = CommonActions.getRIdFromString(context, i)
                }


                BoxWithConstraints(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    val size = maxWidth
                    OutlinedIconButton(
                        modifier = Modifier
                            .size(size)
                            .clip(CircleShape),
                        enabled = playersInfo.iconsId != i,
                        onClick = {
                            changeAveInDataStore(
                                coroutine = coroutine,
                                dataStore = dataStore,
                                id = i
                            )
                        }) {
                        Image(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            painter = painterResource(
                                id = imageId.intValue
                            ),
                            contentDescription = "ave",
                            contentScale = ContentScale.Crop
                        )
                    }
                }

            }
        }

    }
}


private fun changeAveInDataStore(coroutine: CoroutineScope, dataStore: DataStoreManager, id: Int) {
    coroutine.launch {
        dataStore.savePlayersIconsId(
            PlayersInfo(iconsId = id)
        )
    }
}