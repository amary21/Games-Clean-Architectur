package com.amary.codexgamer.ui.detail

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.amary.codexgamer.core.domain.model.Favorite
import com.amary.codexgamer.core.domain.model.Games
import com.amary.codexgamer.core.domain.usecase.GamesUseCase
import kotlinx.coroutines.flow.Flow

class DetailViewModel(private val gamesUseCase: GamesUseCase) : ViewModel() {

    fun insertFavorite(favorite: Favorite) = gamesUseCase.insertFavorite(favorite)

    fun deleteFavorite(gamesId: Int) = gamesUseCase.deleteFavorite(gamesId)

    fun isFavorite(gamesId: Int) = LiveDataReactiveStreams.fromPublisher(gamesUseCase.isFavorite(gamesId))
}