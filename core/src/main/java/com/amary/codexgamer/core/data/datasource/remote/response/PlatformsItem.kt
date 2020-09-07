package com.amary.codexgamer.core.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class PlatformsItem(
	@SerializedName("platform") val platform: Platform? = null,
	@SerializedName("requirements_en") val requirementsEn: RequirementsEn? = null
)