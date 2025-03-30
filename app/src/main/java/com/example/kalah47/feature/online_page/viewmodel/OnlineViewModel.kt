package com.example.kalah47.feature.online_page.viewmodel

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope

class OnlineViewModel: ViewModel() {
    private lateinit var koinNav: NavController
    private lateinit var coroutineScope: CoroutineScope

    private val joinIdTextField = mutableStateOf("")
    private val isGameIdError = mutableStateOf(false)

    val scrollScale = ScrollState


    fun setParams(koinNav: NavController, coroutineScope: CoroutineScope){
        this.koinNav = koinNav
        this.coroutineScope = coroutineScope

    }










}