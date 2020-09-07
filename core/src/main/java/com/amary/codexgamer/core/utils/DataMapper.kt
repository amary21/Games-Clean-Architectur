package com.amary.codexgamer.core.utils

import com.amary.codexgamer.core.data.datasource.local.entity.FavoriteEntity
import com.amary.codexgamer.core.data.datasource.local.entity.GamesEntity
import com.amary.codexgamer.core.data.datasource.local.entity.GamesWithFavorite
import com.amary.codexgamer.core.data.datasource.remote.response.GamesResponse
import com.amary.codexgamer.core.domain.model.Favorite
import com.amary.codexgamer.core.domain.model.Games
import com.amary.codexgamer.core.domain.model.GamesFavorite


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

    fun mapEntityToDomain(input: GamesEntity): Games {
        return Games(
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
    }

    fun mapFavoriteDomainToFavoriteEnitity(favorite: Favorite) = FavoriteEntity(
        inputId = favorite.gamesId
    )

    fun mapListFavoriteEntityToListFavoriteDomain(input: GamesWithFavorite): GamesFavorite {
        val gamesEntity = input.gamesEntity
        val games = Games(
            id = gamesEntity.id,
            name = gamesEntity.name,
            released = gamesEntity.released,
            backgroundImage = gamesEntity.backgroundImage,
            rating = gamesEntity.rating,
            clip = gamesEntity.clip,
            minimumRequirement = gamesEntity.minimumRequirement,
            recommendedRequirement = gamesEntity.recommendedRequirement,
            genres = gamesEntity.genres,
            platforms = gamesEntity.platforms,
            stores = gamesEntity.stores,
            shortScreenshots = gamesEntity.shortScreenshots
        )
        val favoriteEntity = input.favoriteEntity
        val favorite = Favorite(
            gamesId = favoriteEntity.gamesId
        )

        return GamesFavorite(games, favorite)
    }
}