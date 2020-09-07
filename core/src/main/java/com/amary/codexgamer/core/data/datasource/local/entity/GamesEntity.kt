package com.amary.codexgamer.core.data.datasource.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "games")
data class GamesEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "gamesId")
    var id: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "released")
    var released: String,

    @ColumnInfo(name = "background_image")
    var backgroundImage: String,

    @ColumnInfo(name = "rating")
    var rating: Double,

    @ColumnInfo(name = "clip")
    var clip: String,

    @ColumnInfo(name = "minimum_requirement")
    var minimumRequirement: String,

    @ColumnInfo(name = "recommended_requirement")
    var recommendedRequirement: String,

    @ColumnInfo(name = "genres")
    var genres: ArrayList<String>,

    @ColumnInfo(name = "platforms")
    var platforms: ArrayList<String>,

    @ColumnInfo(name = "stores")
    var stores: ArrayList<String>,

    @ColumnInfo(name = "short_screenshots")
    var shortScreenshots: ArrayList<String>

) : Parcelable
