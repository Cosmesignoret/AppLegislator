
package com.duoc.app.data.repository

import android.content.Context

class UsuarioLocalRepository(context: Context) {

    private val preferencias = context.getSharedPreferences(
        "AppLegislatorUsuario",
        Context.MODE_PRIVATE
    )

    fun guardarUsuario(nombre: String, correo: String) {
        preferencias.edit()
            .putString("nombre", nombre.trim())
            .putString("correo", correo.trim().lowercase())
            .apply()
    }

    fun obtenerNombre(): String {
        return preferencias.getString("nombre", "") ?: ""
    }

    fun obtenerCorreo(): String {
        return preferencias.getString("correo", "") ?: ""
    }
}
