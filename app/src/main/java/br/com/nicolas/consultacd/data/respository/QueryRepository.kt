package br.com.nicolas.consultacd.data.respository

import br.com.nicolas.consultacd.data.service.QueryService
import br.com.nicolas.consultacd.domain.DirectModel
import br.com.nicolas.consultacd.models.CepRemote
import br.com.nicolas.consultacd.models.toDirectModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class QueryRepository(
    private val service: QueryService,
    private val coroutineDispatcher: CoroutineDispatcher
) : QueryDataSource {

    override suspend fun getCep(cepCode: String): Flow<CepRemote> = flow {
        val response = service.getCep(cepCode)
        emit(response)
    }.flowOn(coroutineDispatcher)

    override suspend fun getDirect(dddCode: String): Flow<DirectModel> = flow {
        val response = service.getDirect(dddCode)
        emit(response.toDirectModel())
    }.flowOn(coroutineDispatcher)

}