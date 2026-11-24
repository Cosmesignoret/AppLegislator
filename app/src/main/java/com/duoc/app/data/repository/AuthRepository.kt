package com.duoc.app.data.repository

import com.duoc.app.data.remote.api.AuthApi
import com.duoc.app.data.remote.dto.LoginRequestDto
import com.duoc.app.data.remote.dto.RegisterRequestDto

class AuthRepository(
    private val api: AuthApi,
    private val sesionManager: SesionManager
) {
    suspend fun login(correo: String, contrasena: String): Result<Unit> =
        runCatching {
            val response = api.login(LoginRequestDto(correo, contrasena))
            sesionManager.guardarToken(response.token)
        }

    suspend fun register(nombre: String, correo: String, contrasena: String): Result<Unit> =
        runCatching {
            api.register(RegisterRequestDto(nombre, correo, contrasena))
        }
}