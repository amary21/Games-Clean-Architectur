package com.amary.codexgamer.domain.repository

import androidx.paging.PagedList
import com.amary.codexgamer.domain.model.Favorite
import com.amary.codexgamer.domain.model.Games
import com.amary.codexgamer.domain.model.GamesFavorite
import com.amary.codexgamer.domain.model.ResourceState
import io.reactivex.Flowable

interface IGamesRepository {
    fun getAllGames(searchKey: String): Flowable<PagedList<Games>>

    fun getDetailGames(gamesId: Int): Flowable<Games>

    fun getResourceState(): Flowable<ResourceState>

    fun insertFavorite(favorite: Favorite)

    fun getAllFavoriteGames(): Flowable<List<GamesFavorite>>

    fun isFavorite(gamesId: Int): Flowable<Int>

    fun deleteFavorite(gamesId: Int)
}