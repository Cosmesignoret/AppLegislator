package com.duoc.app.data.repository

import android.content.Context

class SesionManager(context: Context) {

    private val preferencias = context.getSharedPreferences(
        "InfoDerechoSesion",
        Context.MODE_PRIVATE
    )

    fun guardarToken(token: String) {
        preferencias.edit().putString(KEY_TOKEN, token).apply()
    }

    fun obtenerToken(): String? = preferencias.getString(KEY_TOKEN, null)

    fun guardarSesion(usuarioId: Int) {
        preferencias.edit().putInt(KEY_USUARIO_ID, usuarioId).apply()
    }

    fun obtenerUsuarioId(): Int = preferencias.getInt(KEY_USUARIO_ID, -1)

    fun sesionIniciada(): Boolean {
        return !obtenerToken().isNullOrBlank() || obtenerUsuarioId() != -1
    }

    fun cerrarSesion() {
        preferencias.edit()
            .remove(KEY_TOKEN)
            .remove(KEY_USUARIO_ID)
            .apply()
    }

    private companion object {
        const val KEY_TOKEN = "token"
        const val KEY_USUARIO_ID = "usuarioId"
    }
}