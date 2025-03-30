package com.example.kalah47.repository

import android.annotation.SuppressLint
import android.content.Context
import com.example.kalah47.R
import com.example.kalah47.core.PLAYERS_ICONS_COUNT
import com.example.kalah47.core.PLAYER_DEFAULT_ICON
import com.example.kalah47.core.PLAYER_ICONS_NAME

class CommonActions {
    companion object {
        @SuppressLint("DiscouragedApi")
        fun getRIdFromString(
            context: Context,
            id: Int,
            imageCount: Int = PLAYERS_ICONS_COUNT,
            imageName: String = PLAYER_ICONS_NAME,
            imageDefault: String = PLAYER_DEFAULT_ICON
        ): Int {
            val result = context.resources.getIdentifier(
                if (id in (1..imageCount)) "$imageName$id" else imageDefault,
                "drawable",
                context.packageName
            )
            return result
        }

        fun getSmallTableThemeById(id: Int): Int {
            return when (id) {
                1 -> R.drawable.background_1_small
                2 -> R.drawable.background_2_small
                3 -> R.drawable.background_3_small
                else -> R.drawable.hehe
            }
        }
    }

}