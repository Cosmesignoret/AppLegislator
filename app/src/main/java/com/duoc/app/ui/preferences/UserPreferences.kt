package com.duoc.app.preferences

import android.content.Context
import androidx.core.content.edit

class UserPreferences(context: Context) {

    private val prefs = context.getSharedPreferences("info_derecho_prefs", Context.MODE_PRIVATE)

    fun getZoom(): Float = prefs.getFloat(KEY_ZOOM, 1f)

    fun saveZoom(value: Float) {
        prefs.edit { putFloat(KEY_ZOOM, value) }
    }

    fun getLastDocument(): String? = prefs.getString(KEY_LAST_DOC, null)

    fun saveLastDocument(id: String) {
        prefs.edit { putString(KEY_LAST_DOC, id) }
    }

    private companion object {
        const val KEY_ZOOM = "zoom"
        const val KEY_LAST_DOC = "last_document"
    }
}
