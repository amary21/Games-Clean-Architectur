package com.amary.codexgamer.favorite

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.amary.codexgamer.domain.usecase.GamesUseCase

class FavoriteViewModel(private val gamesUseCase: GamesUseCase) : ViewModel() {
    fun getAllFavoriteGames() =
        LiveDataReactiveStreams.fromPublisher(gamesUseCase.getAllFavoriteGames())

}