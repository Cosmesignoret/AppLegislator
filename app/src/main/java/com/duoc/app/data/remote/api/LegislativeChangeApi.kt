package com.duoc.app.data.remote.api

import com.duoc.app.data.remote.dto.LegislativeChangeDto
import retrofit2.http.GET

interface LegislativeChangeApi {
    @GET("changes")
    suspend fun getChanges(): List<LegislativeChangeDto>
}