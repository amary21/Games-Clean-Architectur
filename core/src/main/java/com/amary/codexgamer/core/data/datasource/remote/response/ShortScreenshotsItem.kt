package com.amary.codexgamer.core.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class ShortScreenshotsItem(
	@SerializedName("image") val image: String? = null
)