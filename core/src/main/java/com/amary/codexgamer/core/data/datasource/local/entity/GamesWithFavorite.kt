package com.amary.codexgamer.core.data.datasource.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class GamesWithFavorite(
    @Embedded
    var gamesEntity: GamesEntity,

    @Relation(parentColumn = "gamesId", entityColumn = "gamesId")
    var favoriteEntity: FavoriteEntity
)