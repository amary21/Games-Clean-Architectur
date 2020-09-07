package com.amary.codexgamer.core.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class StoresItem(
	@field:SerializedName("store") val store: Store? = null
)