package com.example.kalah47.repository

const val SETTINGS_TABLE_THEME = "table_theme"
const val SETTINGS_PITS_COUNT = "pits_count"
const val SETTINGS_JEWELS_COUNT = "jewels_count"
const val SETTINGS_BOT_ID = "bot_id"
const val SETTINGS_BOT_LEVEL = "bot_level"

data class SettingsData(
    var tableTheme: Int = -1 ,
//    var tableTheme: Long ,
    var pitsCountOneSide: Int = 0,
    var jewelCountInOnePit: Int = 0,
    var botId: Int = -1,
    var botLevel: Int = -1,
)
