package com.amary.codexgamer.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Games(
    val id: Int,
    val name: String,
    val released: String,
    val backgroundImage: String,
    val rating: Double,
    val clip: String,
    val minimumRequirement: String,
    val recommendedRequirement: String,
    val genres: ArrayList<String>,
    val platforms: ArrayList<String>,
    val stores: ArrayList<String>,
    val shortScreenshots: ArrayList<String>
) : Parcelable