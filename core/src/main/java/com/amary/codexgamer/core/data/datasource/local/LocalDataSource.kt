package com.amary.codexgamer.core.data.datasource.local

import com.amary.codexgamer.core.data.datasource.local.entity.FavoriteEntity
import com.amary.codexgamer.core.data.datasource.local.entity.GamesEntity
import com.amary.codexgamer.core.data.datasource.local.room.GamesDao

class LocalDataSource(private val gamesDao: GamesDao) {

    fun getDetailGames(gamesId: Int) = gamesDao.getDetailGames(gamesId)

    fun insertGames(gamesEntity: List<GamesEntity>) = gamesDao.insertGames(gamesEntity)

    fun getAllFavoriteGames() = gamesDao.getAllFavoriteGames()

    fun insertFavorite(favoriteEntity: FavoriteEntity) = gamesDao.insertFavorite(favoriteEntity)

    fun isFavorite(gamesId: Int) = gamesDao.isFavorite(gamesId)

    fun deleteFavorite(gamesId: Int) = gamesDao.deleteFavorite(gamesId)
}