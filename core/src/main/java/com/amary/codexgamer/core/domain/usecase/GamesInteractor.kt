package com.amary.codexgamer.core.domain.usecase

import com.amary.codexgamer.core.domain.model.Favorite
import com.amary.codexgamer.core.domain.repository.IGamesRepository

class GamesInteractor(private val gamesRepository: IGamesRepository) : GamesUseCase {

    override fun getAllGames(searchKey: String) = gamesRepository.getAllGames(searchKey)

    override fun insertFavorite(favorite: Favorite) = gamesRepository.insertFavorite(favorite)

    override fun getAllFavoriteGames() = gamesRepository.getAllFavoriteGames()

    override fun isFavorite(gamesId: Int) = gamesRepository.isFavorite(gamesId)

    override fun deleteFavorite(gamesId: Int) = gamesRepository.deleteFavorite(gamesId)
}