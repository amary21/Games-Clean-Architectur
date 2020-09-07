package com.amary.codexgamer.core.data.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
class FavoriteEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "favoriteId")
    var id: Int,

    @ColumnInfo(name = "gamesId")
    var gamesId: Int
){
    constructor(inputId:Int) : this(0, inputId)
}