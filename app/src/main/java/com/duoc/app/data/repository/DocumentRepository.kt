package com.duoc.app.data.repository

import com.duoc.app.data.model.LegalDocument
import kotlinx.coroutines.flow.Flow

interface DocumentRepository {
    fun getDocuments(): Flow<List<LegalDocument>>
    fun getDocument(id: String): Flow<LegalDocument?>
}
