package com.amary.codexgamer.core.data.datasource.local.room

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amary.codexgamer.core.data.datasource.local.entity.FavoriteEntity
import com.amary.codexgamer.core.data.datasource.local.entity.GamesEntity
import com.amary.codexgamer.core.data.datasource.local.entity.GamesWithFavorite
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface GamesDao {
    @Query("SELECT * FROM games")
    fun getAllGames(): DataSource.Factory<Int, GamesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGames(gamesEntity: List<GamesEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favoriteEntity: FavoriteEntity): Completable

    @Query("SELECT * FROM games , favorite WHERE games.gamesId = favorite.gamesId")
    fun getAllFavoriteGames(): DataSource.Factory<Int, GamesWithFavorite>

    @Query("SELECT EXISTS (SELECT * FROM favorite WHERE gamesId =:gamesId)")
    fun isFavorite(gamesId: Int): Flowable<Int>

    @Query("DELETE FROM favorite WHERE gamesId=:gamesId")
    fun deleteFavorite(gamesId: Int): Completable
}