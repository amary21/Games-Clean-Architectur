package com.amary.codexgamer.core.domain.repository

import androidx.paging.PagedList
import com.amary.codexgamer.core.data.Resource
import com.amary.codexgamer.core.domain.model.Favorite
import com.amary.codexgamer.core.domain.model.Games
import com.amary.codexgamer.core.domain.model.GamesFavorite
import io.reactivex.Flowable

interface IGamesRepository {
    fun getAllGames(searchKey: String): Flowable<Resource<PagedList<Games>>>

    fun insertFavorite(favorite: Favorite)

    fun getAllFavoriteGames(): Flowable<Resource<PagedList<GamesFavorite>>>

    fun isFavorite(gamesId: Int): Flowable<Int>

    fun deleteFavorite(gamesId: Int)
}