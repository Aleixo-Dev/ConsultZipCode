package br.com.nicolas.consultacd.models

import br.com.nicolas.consultacd.domain.DirectModel
import com.google.gson.annotations.SerializedName

data class DirectRemote(
    @SerializedName("cities")
    val cities: List<String>,
    @SerializedName("state")
    val state: String
)

fun DirectRemote.toDirectModel() = DirectModel(
    cities, state
)
