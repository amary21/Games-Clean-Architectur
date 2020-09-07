package com.amary.codexgamer.core.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class ListGamesResponse(
	@field:SerializedName("count") val count: Int? = null,
	@field:SerializedName("next") val next: String? = null,
	@field:SerializedName("previous") val previous: Any? = null,
	@field:SerializedName("results") val results: List<GamesResponse>
)