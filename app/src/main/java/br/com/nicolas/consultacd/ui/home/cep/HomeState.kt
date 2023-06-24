package br.com.nicolas.consultacd.ui.home.cep

import br.com.nicolas.consultacd.domain.DirectModel
import br.com.nicolas.consultacd.models.CepRemote

sealed class HomeState {

    object Loading : HomeState()

    data class Success(
        val data : CepRemote? = null
    ) : HomeState()

    data class SuccessDirect(
        val directModel : DirectModel
    ) : HomeState()

    data class Error(
        val message : String? = null
    ) : HomeState()

    object CepInvalid : HomeState()
}
