package com.amary.codexgamer.core.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class GenresItem(
	@SerializedName("name") val name: String? = null
)