package br.com.nicolas.consultacd.models


import com.google.gson.annotations.SerializedName

data class CepRemote(
    @SerializedName("cep")
    val cep: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("location")
    val location: Location,
    @SerializedName("neighborhood")
    val neighborhood: String,
    @SerializedName("service")
    val service: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("street")
    val street: String
)