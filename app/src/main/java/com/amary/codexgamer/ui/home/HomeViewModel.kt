package com.amary.codexgamer.ui.home

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.amary.codexgamer.core.domain.model.Games
import com.amary.codexgamer.core.domain.usecase.GamesUseCase

class HomeViewModel(private val gamesUseCase: GamesUseCase) : ViewModel() {
    fun getGames(searchKey: String) = LiveDataReactiveStreams.fromPublisher(gamesUseCase.getAllGames(searchKey))

}