package com.duoc.app.data.remote.dto

data class LoginRequestDto(
    val correo: String,
    val contrasena: String
)

data class LoginResponseDto(
    val token: String
)