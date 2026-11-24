package com.duoc.app.data.remote.api

import com.duoc.app.data.remote.dto.LegislativeChangeDto
import retrofit2.http.GET
import retrofit2.http.Path

interface LegislativeChangeApi {
    @GET("changes")
    suspend fun getChanges(): List<LegislativeChangeDto>

    @GET("changes/{id}")
    suspend fun getChangeById(@Path("id") id: String): LegislativeChangeDto
}