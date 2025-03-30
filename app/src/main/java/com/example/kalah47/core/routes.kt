package com.example.kalah47.core

import kotlinx.serialization.Serializable

@Serializable
object RootRoute

@Serializable
object LocalGameRoute

@Serializable
object BotGameRoute

@Serializable
object SettingsRoute

@Serializable
data class OnlineGameRoute(
    val gameId: String,
    val startPlayerId: Int = 0,
    val isCreateLobby: Boolean? = null
)

@Serializable
object OnlineRoute

@Serializable
object GameRuleRoute

@Serializable
object AboutSystemRoute
