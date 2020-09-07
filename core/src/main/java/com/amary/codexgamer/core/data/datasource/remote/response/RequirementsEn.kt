package com.amary.codexgamer.core.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class RequirementsEn(
	@SerializedName("minimum") val minimum: String? = null,
	@SerializedName("recommended") val recommended: String? = null
)