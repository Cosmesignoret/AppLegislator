package com.duoc.app.data.remote.api

import com.duoc.app.data.remote.dto.LoginRequestDto
import com.duoc.app.data.remote.dto.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequestDto): LoginResponseDto
}