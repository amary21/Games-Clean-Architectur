package com.amary.codexgamer.core.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class GamesResponse(
	@field:SerializedName("id") val id: Int,
	@field:SerializedName("name")	val name: String,
	@field:SerializedName("released")	val released: String? = null,
	@field:SerializedName("background_image")	val backgroundImage: String? = null,
	@field:SerializedName("rating") val rating: Double,
	@field:SerializedName("genres") val genres: List<GenresItem?>? = null,
	@field:SerializedName("platforms") val platforms: List<PlatformsItem?>? = null,
	@field:SerializedName("stores") val stores: List<StoresItem?>? = null,
	@field:SerializedName("short_screenshots") val shortScreenshots: List<ShortScreenshotsItem?>? = null,
	@field:SerializedName("clip")	val clip: Clip? = null
)
