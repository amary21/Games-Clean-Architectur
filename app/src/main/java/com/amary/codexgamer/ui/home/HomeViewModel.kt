package com.amary.codexgamer.ui.home

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.amary.codexgamer.domain.usecase.GamesUseCase

class HomeViewModel(private val gamesUseCase: GamesUseCase) : ViewModel() {
    fun getGames(searchKey: String) =
        LiveDataReactiveStreams.fromPublisher(gamesUseCase.getAllGames(searchKey))

    fun getResourceState() = LiveDataReactiveStreams.fromPublisher(gamesUseCase.getResourceState())
}