package com.amary.codexgamer.core.data.datasource.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.amary.codexgamer.core.data.datasource.local.entity.FavoriteEntity
import com.amary.codexgamer.core.data.datasource.local.entity.GamesEntity

@Database(
    entities = [GamesEntity::class, FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(GamesConverters::class)
abstract class GamesDatabase : RoomDatabase() {
    abstract fun gamesDao(): GamesDao
}