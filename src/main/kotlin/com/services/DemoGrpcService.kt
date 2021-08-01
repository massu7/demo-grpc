package com.services

import com.example.ResponseTemplate
import com.example.RequestTemplate
import com.example.DemoGrpcServiceGrpcKt
import kotlinx.coroutines.flow.*
import javax.inject.Singleton

@Singleton
class DemoGrpcService : DemoGrpcServiceGrpcKt.DemoGrpcServiceCoroutineImplBase() {
    override suspend fun unaryCall(request: RequestTemplate): ResponseTemplate {
        return ResponseTemplate.newBuilder().setMessage("Hi, your message is: ${request.name}").build()
    }

    override suspend fun clientSideStreaming(requests: Flow<RequestTemplate>): ResponseTemplate {
        val requestsList = mutableListOf<String>()
        requests.collect { item -> requestsList.add(item.name) }

        return ResponseTemplate.newBuilder().setMessage("Hi, your message is: ${requestsList.toString()}").build()
    }

    override fun serverSideStreaming(request: RequestTemplate): Flow<ResponseTemplate> {
        return flow {
            emit(ResponseTemplate.newBuilder().setMessage("Hi, your message is: ${request.name}").build())
        }
    }

    override fun bidirectionalStreaming(requests: Flow<RequestTemplate>): Flow<ResponseTemplate> {
        return flow {
            requests.collect { item ->
                emit(ResponseTemplate.newBuilder().setMessage("Hi, your message is: ${item.name}").build())
            }
        }
    }
}
