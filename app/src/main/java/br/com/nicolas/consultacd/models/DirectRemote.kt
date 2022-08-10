package br.com.nicolas.consultacd.models


import com.google.gson.annotations.SerializedName

data class DirectRemote(
    @SerializedName("cities")
    val cities: List<String>,
    @SerializedName("state")
    val state: String
)