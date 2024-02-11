package com.example.applicationmeteo.model

import com.google.gson.annotations.SerializedName

data class GeoCodeListResponse(

	@field:SerializedName("generationtime_ms")
	val generationtimeMs: Any? = null,

	@field:SerializedName("results")
	val results: List<GeoCoddeRespone?>? = null
)

data class GeoCoddeRespone(

	@field:SerializedName("elevation")
	val elevation: Any? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("timezone")
	val timezone: String? = null,

	@field:SerializedName("latitude")
	val latitude: Double? = null,

	@field:SerializedName("longitude")
	val longitude: Double? = null,

)
