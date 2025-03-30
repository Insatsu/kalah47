package com.example.kalah47.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.kalah47.core.DATASTORE
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(DATASTORE)

// * Manager for manage dataStore that contain game settings such as Table Color, Pits count on one side, Jewels count in one pit
class DataStoreManager(private val context: Context) {

//  * Settings data part
    suspend fun saveSettingsTableTheme(settingsData: SettingsData){
        context.dataStore.edit {pref ->
//            pref[longPreferencesKey(SETTINGS_TABLE_THEME)] = settingsData.tableTheme
            pref[intPreferencesKey(SETTINGS_TABLE_THEME)] = settingsData.tableTheme
        }
    }
    suspend fun saveSettingsPitsAndJewels(settingsData: SettingsData){
        context.dataStore.edit {pref ->
            pref[intPreferencesKey(SETTINGS_PITS_COUNT)] = settingsData.pitsCountOneSide
            pref[intPreferencesKey(SETTINGS_JEWELS_COUNT)] = settingsData.jewelCountInOnePit
        }
    }
    suspend fun saveSettingsBot(settingsData: SettingsData){
        context.dataStore.edit {pref ->
            pref[intPreferencesKey(SETTINGS_BOT_ID)] = settingsData.botId
            pref[intPreferencesKey(SETTINGS_BOT_LEVEL)] = settingsData.botLevel
        }
    }

    fun getSettings(isDarkTheme: Boolean = false) = context.dataStore.data.map {pref ->
        return@map SettingsData(
            pref[intPreferencesKey(SETTINGS_TABLE_THEME)] ?: 1 ,
//            pref[intPreferencesKey(SETTINGS_TABLE_THEME)] ?: if(isDarkTheme) backgroundDark.value.toLong() else backgroundLight.value.toLong() ,
            pref[intPreferencesKey(SETTINGS_PITS_COUNT)] ?: 6,
            pref[intPreferencesKey(SETTINGS_JEWELS_COUNT)] ?: 6,
            pref[intPreferencesKey(SETTINGS_BOT_ID)] ?: 0,
            pref[intPreferencesKey(SETTINGS_BOT_LEVEL)] ?: 1,
        )
    }


//  * Players Info part
    suspend fun savePlayersIconsId(playersInfo: PlayersInfo){
        context.dataStore.edit {pref ->
            pref[intPreferencesKey(PLAYERS_INFO_ICONS_ID)] = playersInfo.iconsId
        }
    }
    suspend fun savePlayersNickname(playersInfo: PlayersInfo){
        context.dataStore.edit {pref ->
            pref[stringPreferencesKey(PLAYERS_INFO_NICKNAME)] = playersInfo.nickName
        }
    }
    fun getPlayersInfo() = context.dataStore.data.map {pref ->
        return@map PlayersInfo(
            pref[intPreferencesKey(PLAYERS_INFO_ICONS_ID)] ?: 1,
            pref[stringPreferencesKey(PLAYERS_INFO_NICKNAME)] ?: "Новичочек"
        )
    }

}