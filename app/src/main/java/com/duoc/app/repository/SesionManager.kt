package com.duoc.app.repository

import android.content.Context

class SesionManager(context: Context) {

    private val preferencias = context.getSharedPreferences(
        "InfoDerechoSesion",
        Context.MODE_PRIVATE
    )

    fun guardarSesion(usuarioId: Int) {
        preferencias.edit()
            .putInt("usuarioId", usuarioId)
            .apply()
    }

    fun obtenerUsuarioId(): Int {
        return preferencias.getInt("usuarioId", -1)
    }

    fun sesionIniciada(): Boolean {
        return obtenerUsuarioId() != -1
    }

    fun cerrarSesion() {
        preferencias.edit()
            .remove("usuarioId")
            .apply()
    }
}