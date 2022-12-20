package br.com.nicolas.consultacd.models


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("coordinates")
    val coordinates: Coordinates?,
    @SerializedName("type")
    val type: String
)