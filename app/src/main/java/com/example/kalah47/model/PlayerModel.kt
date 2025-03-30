package com.example.kalah47.model

import com.example.kalah47.repository.PlayersInfo

data class PlayerModel(
    val info: PlayersInfo = PlayersInfo(),
    var id: Int = -1,
)
