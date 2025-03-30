package com.example.kalah47.feature.online_page.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kalah47.R
import com.example.kalah47.core.FIRESTORE
import com.example.kalah47.core.OnlineGameRoute
import com.example.kalah47.feature.common_components.ResizedText
import com.example.kalah47.model.KalahModelFirestore
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore

@Composable
fun JoinInLobby(
    textField: MutableState<String>,
    isGameIdError: MutableState<Boolean>,
    koinNav: NavController
) {
    val joinPartHeight = 0.6f
    val horizontalPadding = 10.dp

    val errorType = remember {
        mutableIntStateOf(0)
    }

//  * Join part title
    ResizedText(
        stringResource(id = R.string.online_text_or_join),
        defaultTextStyle = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(top = 30.dp, bottom = 10.dp)
    )

    Row(
        Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = horizontalPadding),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    )
    {
//      * TextField for game lobby id
        JoinTextField(
            textField,
            isGameIdError,
            errorType,
            Modifier
                .weight(0.25f)
                .fillMaxHeight(joinPartHeight)
        ) { tryToJoin(textField, koinNav, isGameIdError) }

        Spacer(modifier = Modifier.weight(0.1f))
//      * Button for join in lobby
        OutlinedButton(
            modifier = Modifier
                .fillMaxHeight(joinPartHeight)
                .weight(0.25f),
            onClick = {
                tryToJoin(textField, koinNav, isGameIdError)
            },
            enabled = textField.value.isNotEmpty() && !isGameIdError.value ,
            contentPadding = PaddingValues(10.dp)
        ) {
            ResizedText(
                stringResource(id = R.string.online_join_button),
                defaultTextStyle = MaterialTheme.typography.titleLarge
            )
        }
    }
//  * Message on error
    if (isGameIdError.value)
        Box(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalPadding),
            contentAlignment = Alignment.TopStart
        ) {
            ResizedText(
                when (errorType.intValue) {
                    1 -> stringResource(id = R.string.online_join_error_invalid_symbol)
                    2 -> stringResource(id = R.string.online_join_error_length)
                    else -> stringResource(id = R.string.online_join_error_not_exist)

                },
                color = MaterialTheme.colorScheme.error,
                defaultTextStyle = MaterialTheme.typography.labelLarge
            )
        }


}


// ====================================
@Composable
private fun JoinTextField(
    textField: MutableState<String>,
    isError: MutableState<Boolean>,
    errorType: MutableIntState,
    modifier: Modifier,
    onKeyboardDone: () -> Unit
) {
    OutlinedTextField(
        value = textField.value,
        onValueChange = { value ->
            textField.value = value

            if (value.isEmpty()) {
                isError.value = false
                return@OutlinedTextField
            } else if (value.contains(Regex("\\D"))) {
                errorType.intValue = 1
                isError.value = true
                return@OutlinedTextField
            } else if (value.length != 5) {
                errorType.intValue = 2
                isError.value = true
                return@OutlinedTextField
            } else {
                isError.value = false
                errorType.intValue = 0
            }
        },
        modifier = modifier,
        placeholder = {
            ResizedText(
                stringResource(id = R.string.online_join_textfield_placeholder),
                defaultTextStyle = MaterialTheme.typography.titleLarge
            )
        },
        isError = isError.value,
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            focusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,

            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,

            disabledIndicatorColor = MaterialTheme.colorScheme.secondaryContainer,
//            focusedIndicatorColor = MaterialTheme.colorScheme.background,

//            focusedIndicatorColor = MaterialTheme.colorScheme.onSecondaryContainer,
//            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSecondaryContainer,
            errorContainerColor = MaterialTheme.colorScheme.errorContainer,
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        keyboardActions = KeyboardActions(onDone = {
            onKeyboardDone()
        })
    )


}


private fun tryToJoin(
    textField: MutableState<String>,
    koinNav: NavController,
    isGameIdError: MutableState<Boolean>
) {
    Firebase.firestore.collection(FIRESTORE)
        .document(textField.value)
        .get()
        .addOnSuccessListener { kalahModel ->
            onGetFirestoreData(kalahModel, koinNav, textField, isGameIdError)
        }
}


private fun onGetFirestoreData(
    kalahModel: DocumentSnapshot?,
    koinNav: NavController,
    textField: MutableState<String>,
    isGameIdError: MutableState<Boolean>
) {
    val model = kalahModel?.toObject(KalahModelFirestore::class.java)
    if (model == null) {
        isGameIdError.value = true
        return
    }
    val jointId = if (model.playersJoint.isNotEmpty()) (model.playersJoint.last().id + 1) % 2 else 0
    koinNav.navigate(
        OnlineGameRoute(
            gameId = textField.value,
            startPlayerId = jointId,
            isCreateLobby = false
        )
    )

}