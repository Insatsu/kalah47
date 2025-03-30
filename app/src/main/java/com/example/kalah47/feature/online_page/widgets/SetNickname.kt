package com.example.kalah47.feature.online_page.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.kalah47.R
import com.example.kalah47.core.PLAYER_NICKNAME_MAX_LENGTH
import com.example.kalah47.core.PLAYER_NICKNAME_MIN_LENGTH
import com.example.kalah47.repository.DataStoreManager
import com.example.kalah47.repository.PlayersInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import java.util.regex.Pattern


@Composable
fun SetNickname(
    dataStore: DataStoreManager = koinInject(),
    playerInfo: PlayersInfo,
) {
    val coroutine = rememberCoroutineScope()
    val nickname = remember {
        mutableStateOf("")
    }
    val isNicknameError = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(playerInfo.nickName) {
        nickname.value = playerInfo.nickName
    }

    val txtFieldWeight = 0.9f

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {

        NicknameField(
            nickname = nickname,
            isNicknameError = isNicknameError,
            modifier = Modifier.weight(txtFieldWeight),
            onKeyboardDone = { saveNickname(coroutine, dataStore, nickname) }
        )

        IconButton(
            modifier = Modifier
                .weight(1 - txtFieldWeight),
            enabled = !isNicknameError.value && nickname.value != playerInfo.nickName,
            onClick = {
                saveNickname(coroutine, dataStore, nickname)
            },
            colors = IconButtonDefaults.iconButtonColors(

            )
        ) {
            Icon(
                Icons.Rounded.Check,
                contentDescription = "nick"
            )
        }
    }
}


@Composable
private fun NicknameField(
    nickname: MutableState<String>,
    isNicknameError: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    onKeyboardDone: () -> Unit
) {
    val errorType = remember {
        mutableIntStateOf(0)
    }

    Column(modifier) {
//      * Textfield title
        Text(
            stringResource(id = R.string.online_nickname_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            textAlign = TextAlign.Start
        )
//      * Textfield
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 1.dp)
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.onSecondaryContainer,
                    RoundedCornerShape(8.dp)
                ),
            value = nickname.value,
            onValueChange = { value ->
//              * Validation
                val pattern = Pattern.compile("[^a-zA-Z0-9а-яА-Я_]")
                val isInvalid = pattern.matcher(value).find()
//              * Check for empty, max and min values, need symbols

                if (value.isEmpty())
                    errorType.intValue = 1
                else if (value.length > PLAYER_NICKNAME_MAX_LENGTH)
                    errorType.intValue = 2
                else if (value.length < PLAYER_NICKNAME_MIN_LENGTH)
                    errorType.intValue = 3
                else if (isInvalid)
                    errorType.intValue = 4
                else
                    errorType.intValue = 0

                isNicknameError.value =
                    value.isEmpty() || value.length > PLAYER_NICKNAME_MAX_LENGTH || value.length < PLAYER_NICKNAME_MIN_LENGTH || isInvalid

                nickname.value = value
            },
            shape = RoundedCornerShape(8.dp),
            placeholder = {
                Text(
                    stringResource(id = R.string.online_nickname_placeholder)
                )
            },
            keyboardActions = KeyboardActions(onDone = {
                if (isNicknameError.value)
                    return@KeyboardActions
                onKeyboardDone()
            }),

            singleLine = true,
            isError = isNicknameError.value,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                focusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,

                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,

                disabledIndicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                focusedIndicatorColor = MaterialTheme.colorScheme.background,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
                errorContainerColor = MaterialTheme.colorScheme.errorContainer,
                errorIndicatorColor = MaterialTheme.colorScheme.background
            )
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(50.dp)
        ) {
            //      * Error message
            if (isNicknameError.value)
                Text(
                    text = when (errorType.intValue) {
                        1 -> stringResource(id = R.string.online_nickname_empty_nick)
                        2 ->
                            stringResource(
                                id = R.string.online_nickname_long_nick,
                                PLAYER_NICKNAME_MAX_LENGTH
                            )
                        3 ->
                            stringResource(
                                id = R.string.online_nickname_short_nick,
                                PLAYER_NICKNAME_MIN_LENGTH
                            )

                        else ->
                            stringResource(
                                id = R.string.online_nickname_invalid_symbols
                            )
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    maxLines = 3
                )


            //      * Textfield letters counter
            Text(
                " ${nickname.value.length} / $PLAYER_NICKNAME_MAX_LENGTH",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                textAlign = TextAlign.End
            )
        }

    }

}

private fun saveNickname(
    coroutine: CoroutineScope,
    dataStore: DataStoreManager,
    nickname: MutableState<String>
) {
    coroutine.launch {
        dataStore.savePlayersNickname(PlayersInfo(nickName = nickname.value))
    }
}