package br.com.nicolas.consultacd.di

import br.com.nicolas.consultacd.data.respository.QueryDataSource
import br.com.nicolas.consultacd.data.respository.QueryRepository
import br.com.nicolas.consultacd.data.service.QueryService
import br.com.nicolas.consultacd.ui.home.cep.HomeViewModel
import br.com.nicolas.consultacd.utils.URL
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val instance = module {

    single { provideRetrofit() }
    factory<QueryDataSource> {
        QueryRepository(service = get(), Dispatchers.IO)
    }

    viewModel { HomeViewModel(get()) }

}

private fun provideRetrofit(): QueryService {
    return Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(QueryService::class.java)
}