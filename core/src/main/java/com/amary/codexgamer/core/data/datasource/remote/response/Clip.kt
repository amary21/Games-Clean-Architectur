package com.amary.codexgamer.core.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class Clip(
	@SerializedName("video") val video: String? = null
)