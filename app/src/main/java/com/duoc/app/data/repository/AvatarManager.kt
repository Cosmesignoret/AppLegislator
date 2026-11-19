
package com.duoc.app.data.repository

import android.content.Context
import android.net.Uri

class AvatarManager(private val context: Context) {

    private val preferencias = context.getSharedPreferences(
        "InfoDerechoAvatar",
        Context.MODE_PRIVATE
    )

    fun guardarAvatar(uri: Uri) {
        val archivo = context.filesDir.resolve("avatar.jpg")

        context.contentResolver.openInputStream(uri)?.use { entrada ->
            archivo.outputStream().use { salida ->
                entrada.copyTo(salida)
            }
        }

        preferencias.edit()
            .putString("avatar", archivo.absolutePath)
            .apply()
    }

    fun obtenerAvatar(): Uri? {
        val ruta = preferencias.getString("avatar", null) ?: return null
        val archivo = java.io.File(ruta)

        return if (archivo.exists()) {
            Uri.fromFile(archivo)
        } else {
            null
        }
    }
}
