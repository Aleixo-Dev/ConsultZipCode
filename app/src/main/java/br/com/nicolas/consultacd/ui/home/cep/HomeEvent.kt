package br.com.nicolas.consultacd.ui.home.cep


sealed class HomeEvent {
    data class OnFetchCep(
        val codeCep: String
    ) : HomeEvent()
    data class OnFetchDirect(
        val dddCode : String
    ) : HomeEvent()
    object InvalidCode : HomeEvent()
}
