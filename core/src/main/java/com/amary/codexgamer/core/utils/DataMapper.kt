package com.amary.codexgamer.core.utils

import com.amary.codexgamer.core.data.datasource.local.entity.FavoriteEntity
import com.amary.codexgamer.core.data.datasource.local.entity.GamesEntity
import com.amary.codexgamer.core.data.datasource.local.entity.GamesWithFavorite
import com.amary.codexgamer.core.data.datasource.remote.response.GamesResponse
import com.amary.codexgamer.domain.model.Favorite
import com.amary.codexgamer.domain.model.Games
import com.amary.codexgamer.domain.model.GamesFavorite


object DataMapper {

    fun mapResponsesToEntities(input: List<GamesResponse>): List<GamesEntity> {
        val gamesEntity = ArrayList<GamesEntity>()
        input.map {
            val genres = ArrayList<String>()
            val platforms = ArrayList<String>()
            val stores = ArrayList<String>()
            val screenShots = ArrayList<String>()
            var minimum = ""
            var recommended = ""

            if (it.genres != null)
                for (genre in it.genres) {
                    genre?.name?.let { item -> genres.add(item) }
                }

            if (it.platforms != null)
                for (platform in it.platforms) {
                    platform?.platform?.name?.let { item -> platforms.add(item) }
                    if (platform?.platform?.name == "PC") {
                        minimum =
                            if (platform.requirementsEn?.minimum != null) platform.requirementsEn.minimum else ""
                        recommended =
                            if (platform.requirementsEn?.recommended != null) platform.requirementsEn.recommended else ""
                    }
                }

            if (it.stores != null)
                for (store in it.stores) {
                    store?.store?.name?.let { item -> stores.add(item) }
                }

            if (it.shortScreenshots != null)
                for (screenshot in it.shortScreenshots) {
                    screenshot?.image?.let { item -> screenShots.add(item) }
                }

            val games = GamesEntity(
                id = it.id,
                name = it.name,
                released = it.released ?: "",
                backgroundImage = it.backgroundImage ?: "",
                rating = it.rating,
                clip = it.clip?.video ?: "",
                minimumRequirement = minimum,
                recommendedRequirement = recommended,
                genres = genres,
                platforms = platforms,
                stores = stores,
                shortScreenshots = screenShots
            )
            gamesEntity.add(games)
        }
        return gamesEntity
    }

    fun mapEntityToDomain(input: GamesEntity) = Games(
        id = input.id,
        name = input.name,
        released = input.released,
        backgroundImage = input.backgroundImage,
        rating = input.rating,
        clip = input.clip,
        minimumRequirement = input.minimumRequirement,
        recommendedRequirement = input.recommendedRequirement,
        genres = input.genres,
        platforms = input.platforms,
        stores = input.stores,
        shortScreenshots = input.shortScreenshots
    )

    fun mapFavoriteDomainToFavoriteEntity(favorite: Favorite) = FavoriteEntity(
        inputId = favorite.gamesId
    )

    fun mapListFavoriteEntityToListFavoriteDomain(input: List<GamesWithFavorite>): List<GamesFavorite> {
        val gamesFavoriteList = ArrayList<GamesFavorite>()
        input.map {
            val games = Games(
                id = it.gamesEntity.id,
                name = it.gamesEntity.name,
                released = it.gamesEntity.released,
                backgroundImage = it.gamesEntity.backgroundImage,
                rating = it.gamesEntity.rating,
                clip = it.gamesEntity.clip,
                minimumRequirement = it.gamesEntity.minimumRequirement,
                recommendedRequirement = it.gamesEntity.recommendedRequirement,
                genres = it.gamesEntity.genres,
                platforms = it.gamesEntity.platforms,
                stores = it.gamesEntity.stores,
                shortScreenshots = it.gamesEntity.shortScreenshots
            )
            val favorite = Favorite(
                gamesId = it.favoriteEntity.gamesId
            )

            val gamesFavorite = GamesFavorite(
                games = games,
                favorite = favorite
            )

            gamesFavoriteList.add(gamesFavorite)
        }

        return gamesFavoriteList
    }
}