package com.amary.codexgamer.core.domain.usecase

import androidx.paging.PagedList
import com.amary.codexgamer.core.data.ResourceState
import com.amary.codexgamer.core.domain.model.Favorite
import com.amary.codexgamer.core.domain.model.Games
import com.amary.codexgamer.core.domain.model.GamesFavorite
import io.reactivex.Flowable

interface GamesUseCase {
    fun getAllGames(searchKey: String): Flowable<PagedList<Games>>

    fun getResourceState(): Flowable<ResourceState>

    fun insertFavorite(favorite: Favorite)

    fun getAllFavoriteGames(): Flowable<List<GamesFavorite>>

    fun isFavorite(gamesId: Int): Flowable<Int>

    fun deleteFavorite(gamesId: Int)
}