package br.com.nicolas.consultacd.data.service

import br.com.nicolas.consultacd.models.CepRemote
import br.com.nicolas.consultacd.models.DirectRemote
import retrofit2.http.GET
import retrofit2.http.Path

interface QueryService {

    @GET("cep/v2/{cep}")
    suspend fun getCep(
        @Path("cep") cep : String
    ) : CepRemote

    @GET("ddd/v1/{ddd}")
    suspend fun getDirect(
        @Path("ddd") ddd : String
    ) : DirectRemote

}