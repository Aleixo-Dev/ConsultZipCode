package br.com.nicolas.consultacd.ui.home.cep

import br.com.nicolas.consultacd.models.CepRemote

sealed class HomeState {

    object Loading : HomeState()

    data class Success(
        val data : CepRemote? = null
    ) : HomeState()

    data class SuccessDirect(
        val cities : List<String>? = emptyList()
    ) : HomeState()

    data class Error(
        val message : String? = null
    ) : HomeState()

    object CepInvalid : HomeState()
}
