package com.amary.codexgamer.detail

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.amary.codexgamer.domain.model.Favorite
import com.amary.codexgamer.domain.usecase.GamesUseCase

class DetailViewModel(private val gamesUseCase: GamesUseCase) : ViewModel() {

    fun insertFavorite(favorite: Favorite) = gamesUseCase.insertFavorite(favorite)

    fun deleteFavorite(gamesId: Int) = gamesUseCase.deleteFavorite(gamesId)

    fun isFavorite(gamesId: Int) =
        LiveDataReactiveStreams.fromPublisher(gamesUseCase.isFavorite(gamesId))
}