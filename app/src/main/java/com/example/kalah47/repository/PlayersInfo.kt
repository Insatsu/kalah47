package com.example.kalah47.repository


const val PLAYERS_INFO_ICONS_ID = "icons_id"
const val PLAYERS_INFO_NICKNAME = "nickname"

data class PlayersInfo(
    val iconsId: Int = 0,
    val nickName: String = "*-*"
)
