package com.duoc.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.app.data.model.LegalDocument
import com.duoc.app.data.repository.DocumentRepository
import com.duoc.app.preferences.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn

class ReaderViewModel(
    private val repository: DocumentRepository,
    private val preferences: UserPreferences
) : ViewModel() {

    private val _zoom = MutableStateFlow(preferences.getZoom())
    val zoom: StateFlow<Float> = _zoom.asStateFlow()

    fun getDocument(id: String): StateFlow<LegalDocument?> =
        repository.getDocument(id)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun increaseZoom() = applyZoom(_zoom.value * STEP_FACTOR)
    fun decreaseZoom() = applyZoom(_zoom.value / STEP_FACTOR)
    fun zoomByGesture(factor: Float) = applyZoom(_zoom.value * factor)

    fun registerOpenedDocument(id: String) = preferences.saveLastDocument(id)

    private fun applyZoom(newValue: Float) {
        val clamped = newValue.coerceIn(MIN_ZOOM, MAX_ZOOM)
        _zoom.value = clamped
        preferences.saveZoom(clamped)
    }

    private companion object {
        const val MIN_ZOOM = 0.75f
        const val MAX_ZOOM = 3.5f
        const val STEP_FACTOR = 1.15f
    }
}
