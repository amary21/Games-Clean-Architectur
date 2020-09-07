package com.amary.codexgamer.core.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class GamesResponse(
	@SerializedName("id") val id: Int,
	@SerializedName("name") val name: String,
	@SerializedName("released") val released: String? = null,
	@SerializedName("background_image") val backgroundImage: String? = null,
	@SerializedName("rating") val rating: Double,
	@SerializedName("genres") val genres: List<GenresItem?>? = null,
	@SerializedName("platforms") val platforms: List<PlatformsItem?>? = null,
	@SerializedName("stores") val stores: List<StoresItem?>? = null,
	@SerializedName("short_screenshots") val shortScreenshots: List<ShortScreenshotsItem?>? = null,
	@SerializedName("clip") val clip: Clip? = null
)
