package com.duoc.app.data.remote.dto

data class LoginRequestDto(
    val correo: String,
    val contrasena: String
)

data class LoginResponseDto(
    val token: String
)

data class RegisterRequestDto(
    val nombre: String,
    val correo: String,
    val contrasena: String
)