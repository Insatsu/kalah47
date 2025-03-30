package com.example.kalah47.feature.game_page.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.kalah47.core.FIRESTORE
import com.example.kalah47.core.MY_TAG
import com.example.kalah47.model.KalahGameStatus
import com.example.kalah47.model.KalahModel
import com.example.kalah47.model.KalahModelFirestore
import com.example.kalah47.model.PlayerModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore

class PitsVMOnline : PitsViewModel() {
    override val title: String = "PITsVMOnline"
    var playerId: Int = -1

    private var isFetched: Boolean = false
    private var listener: ListenerRegistration? = null

    override val kalahModel: KalahModel = KalahModel(gameStatus = mutableStateOf(KalahGameStatus.CREATED))

    override fun setKalahSettings(pitsCountOneSide: Int, jewelsCount: Int) {
        if (kalahModel.gameStatus.value == KalahGameStatus.PROGRESS)
            return
        super.setKalahSettings(pitsCountOneSide, jewelsCount)
        pushModelToFirestore(kalahModel.gameStatus.value)
    }

    override fun makeMoveFrom(id: Int): Boolean {
        if (playerId != kalahModel.currentPlayer.intValue)
            return false

        val res = super.makeMoveFrom(id)

        if (kalahModel.winner.intValue != -1) {
            pushModelToFirestore(KalahGameStatus.FINISHED)
            return res
        }


        pushModelToFirestore(KalahGameStatus.PROGRESS)

        return res
    }

    fun createModelInFirestore(
        gameStatus: KalahGameStatus = KalahGameStatus.CREATED,
        playerModel: PlayerModel
    ) {
        Log.d(MY_TAG, "$title createModelInFirestore")

        kalahModel.gameStatus.value = gameStatus

        kalahModel.playersJoint[0] = playerModel
        playerId = playerModel.id

        pushModelToFirestore(gameStatus)
    }

    private fun pushModelToFirestore(gameStatus: KalahGameStatus) {
        Log.d(MY_TAG, "$title pushModelToFireStore\nModel: ${kalahModel}")

        if (kalahModel.gameId.value == "-1") {
            return
        }

        kalahModel.gameStatus.value = gameStatus

        Firebase.firestore.collection(FIRESTORE)
            .document(kalahModel.gameId.value)
            .set(KalahModelFirestore.fromKalahModel(kalahModel))
    }

    fun restoreFromFirestore(playerModel: PlayerModel) {
        Log.d(MY_TAG, "$title restoreFromFireStore\nModel: ${kalahModel}")

//      ?
        if (kalahModel.gameId.value == "-1") {
            return
        }

        Firebase.firestore.collection(FIRESTORE)
            .document(kalahModel.gameId.value)
            .get()
            .addOnSuccessListener { kalahModel ->
                val model =
                    kalahModel!!.toObject(KalahModelFirestore::class.java)!!.toKalahModel()
                model.gameStatus.value = KalahGameStatus.JOINED

                model.playersJoint.add(playerModel)

//              * Clear list
                if (model.playersJoint.size > 2) {
                    model.playersJoint.removeRange(0, model.playersJoint.size - 2)
                }
//              * Set local model and data as restored
                this.kalahModel.copyFrom(model)
                playerId = playerModel.id

                Log.d(MY_TAG, "success restore data: ${this.kalahModel}")

                initPitsPositions()

                pushModelToFirestore(KalahGameStatus.JOINED)
            }
        Log.d(MY_TAG, "$title restoreFromFireStore end\nModel: ${kalahModel}")
    }

    fun fetchGameModel() {
        if (kalahModel.gameId.value == "-1" || isFetched) {
            return
        }
        Log.d(MY_TAG, "$title fetchGameModel")

        listener = Firebase.firestore.collection(FIRESTORE)
            .document(kalahModel.gameId.value)
            .addSnapshotListener { value, _ ->
                Log.d(MY_TAG, "$title listener")

                val model = value?.toObject(KalahModelFirestore::class.java)
                if (model != null)
                {
                    kalahModel.copyFrom(model)
                }
                isFetched = true
            }
    }

    override fun onCleared() {
        listener?.remove()
        kalahModel.gameStatus.value = KalahGameStatus.PAUSED
        kalahModel.playersJoint.removeIf { it.id == playerId }
        Firebase.firestore.collection(FIRESTORE)
            .document(kalahModel.gameId.value)
            .set(KalahModelFirestore.fromKalahModel(kalahModel))

        super.onCleared()
    }

}