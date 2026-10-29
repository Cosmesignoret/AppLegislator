package com.duoc.app.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.app.data.model.LegalDocument
import com.duoc.app.data.repository.DocumentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class DocumentListViewModel(private val repository: DocumentRepository) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    val documents: StateFlow<List<LegalDocument>> =
        repository.getDocuments()
            .combine(_query) { docs, text ->
                if (text.isBlank()) docs
                else docs.filter {
                    it.title.contains(text, ignoreCase = true) ||
                        it.category.contains(text, ignoreCase = true) ||
                        it.content.contains(text, ignoreCase = true)
                }
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun updateQuery(newText: String) {
        _query.value = newText
    }
}
