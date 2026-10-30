package com.duoc.app.data.repository

import com.duoc.app.data.model.LegislativeChange
import kotlinx.coroutines.flow.Flow

interface LegislativeChangeRepository {
    fun getChanges(): Flow<List<LegislativeChange>>
}
