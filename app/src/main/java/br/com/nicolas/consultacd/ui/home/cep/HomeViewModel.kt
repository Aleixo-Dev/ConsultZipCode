package br.com.nicolas.consultacd.ui.home.cep

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.nicolas.consultacd.data.respository.QueryDataSource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(
    private val dataSource: QueryDataSource
) : ViewModel() {

    private val _homeState = MutableLiveData<HomeState>()
    val homeState: LiveData<HomeState> get() = _homeState

    fun interactCep(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnFetchCep -> loadCep(event.codeCep)
            HomeEvent.InvalidCode -> setHomeState(HomeState.CepInvalid)
            is HomeEvent.OnFetchDirect -> loadDirect(event.dddCode)
        }
    }

    private fun loadDirect(dddCode: String) {
        viewModelScope.launch {
            dataSource.getDirect(dddCode)
                .onStart { setHomeState(HomeState.Loading) }
                .catch { setHomeState(HomeState.Error(it.message)) }
                .collect {
                    setHomeState(HomeState.SuccessDirect(it.cities))
                }
        }
    }

    private fun loadCep(cepCode: String) {
        viewModelScope.launch {
            dataSource.getCep(cepCode)
                .onStart {
                    setHomeState(HomeState.Loading)
                }
                .catch {
                    setHomeState(HomeState.Error(it.message))
                }
                .collect {
                    setHomeState(HomeState.Success(it))
                }
        }
    }

    private fun setHomeState(newState: HomeState) {
        _homeState.value = newState
    }
}