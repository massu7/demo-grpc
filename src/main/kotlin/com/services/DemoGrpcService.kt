package com.services

import com.example.ResponseTemplate
import com.example.RequestTemplate
import com.example.DemoGrpcServiceGrpcKt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton

@Singleton
class DemoGrpcService : DemoGrpcServiceGrpcKt.DemoGrpcServiceCoroutineImplBase() {
    override suspend fun send(request: RequestTemplate): ResponseTemplate {
        return ResponseTemplate.newBuilder().setMessage("Hi, your message is: ${request.name}").build()
    }

    override suspend fun clientSideStreaming(requests: Flow<RequestTemplate>): ResponseTemplate {
        return ResponseTemplate.newBuilder().setMessage("TESTE").build()
    }

    override fun serverSideStreaming(request: RequestTemplate): Flow<ResponseTemplate> {
        return flow {
            emit(ResponseTemplate.newBuilder().setMessage("TESTE").build())
        }
    }

    override fun bidirectionalStreaming(requests: Flow<RequestTemplate>): Flow<ResponseTemplate> {
        return flow {
            requests.collect {
                emit(ResponseTemplate.newBuilder().setMessage("TESTE").build())
            }
        }
    }
}
