package com.amary.codexgamer.domain.usecase

import com.amary.codexgamer.domain.model.Favorite
import com.amary.codexgamer.domain.model.Games
import com.amary.codexgamer.domain.repository.IGamesRepository
import io.reactivex.Flowable

class GamesInteractor(private val gamesRepository: IGamesRepository) : GamesUseCase {

    override fun getAllGames(searchKey: String) = gamesRepository.getAllGames(searchKey)

    override fun getDetailGames(gamesId: Int) = gamesRepository.getDetailGames(gamesId)

    override fun getResourceState() = gamesRepository.getResourceState()

    override fun insertFavorite(favorite: Favorite) = gamesRepository.insertFavorite(favorite)

    override fun getAllFavoriteGames() = gamesRepository.getAllFavoriteGames()

    override fun isFavorite(gamesId: Int) = gamesRepository.isFavorite(gamesId)

    override fun deleteFavorite(gamesId: Int) = gamesRepository.deleteFavorite(gamesId)
}