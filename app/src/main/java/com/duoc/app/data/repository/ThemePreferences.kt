
package com.duoc.app.data.repository

import android.content.Context

class ThemePreferences(context: Context) {

    private val preferences = context.getSharedPreferences(
        "app_theme_preferences",
        Context.MODE_PRIVATE
    )

    fun obtenerModoOscuro(): Boolean {
        return preferences.getBoolean("modo_oscuro", false)
    }

    fun guardarModoOscuro(activado: Boolean) {
        preferences.edit()
            .putBoolean("modo_oscuro", activado)
            .apply()
    }
}
