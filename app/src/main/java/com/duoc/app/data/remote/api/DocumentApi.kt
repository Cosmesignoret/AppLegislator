package com.duoc.app.data.remote.api

import com.duoc.app.data.remote.dto.DocumentDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DocumentApi {
    @GET("documents")
    suspend fun getDocuments(): List<DocumentDto>

    @GET("documents/{id}")
    suspend fun getDocument(@Path("id") id: String): DocumentDto
}