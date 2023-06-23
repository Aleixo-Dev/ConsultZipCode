package br.com.nicolas.consultacd.data.respository

import br.com.nicolas.consultacd.domain.DirectModel
import br.com.nicolas.consultacd.models.CepRemote
import kotlinx.coroutines.flow.Flow

interface QueryDataSource {

    suspend fun getCep(cepCode : String): Flow<CepRemote>
    suspend fun getDirect(dddCode : String) : Flow<DirectModel>
}