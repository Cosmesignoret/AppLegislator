package com.duoc.app.data.repository

import com.duoc.app.data.model.DocumentDao
import com.duoc.app.data.model.DocumentEntity
import com.duoc.app.data.model.LegalDocument
import com.duoc.app.data.remote.api.DocumentApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NetworkDocumentRepository(
    private val dao: DocumentDao,
    private val api: DocumentApi
) : DocumentRepository {

    override fun getDocuments(): Flow<List<LegalDocument>> =
        dao.getAll().map { entities ->
            entities.map { LegalDocument(it.id, it.title, it.category, it.content) }
        }

    override fun getDocument(id: String): Flow<LegalDocument?> =
        dao.getById(id).map { entity ->
            entity?.let { LegalDocument(it.id, it.title, it.category, it.content) }
        }

    override suspend fun refresh() {
        val remote = api.getDocuments()
        dao.upsertAll(remote.map { DocumentEntity(it.id, it.title, it.category, it.content) })
    }
}