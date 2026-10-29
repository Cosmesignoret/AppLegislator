
package com.duoc.app.utils

object Validaciones {

    fun correoValido(correo: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS
            .matcher(correo.trim())
            .matches()
    }

    fun contrasenaValida(contrasena: String): Boolean {
        return contrasena.length >= 6
    }
}
