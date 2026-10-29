package com.duoc.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.app.data.model.LegislativeChange
import com.duoc.app.data.repository.LegislativeChangeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class LegislativeChangesViewModel(
    repository: LegislativeChangeRepository
) : ViewModel() {

    val changes: StateFlow<List<LegislativeChange>> =
        repository.getChanges()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
}
