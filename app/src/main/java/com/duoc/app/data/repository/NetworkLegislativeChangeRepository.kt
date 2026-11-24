package com.duoc.app.data.repository

import com.duoc.app.data.model.LegislativeChange
import com.duoc.app.data.model.LegislativeChangeDao
import com.duoc.app.data.model.LegislativeChangeEntity
import com.duoc.app.data.model.SegmentType
import com.duoc.app.data.model.TextSegment
import com.duoc.app.data.remote.api.LegislativeChangeApi
import com.duoc.app.data.remote.dto.LegislativeChangeDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class NetworkLegislativeChangeRepository(
    private val api: LegislativeChangeApi,
    private val dao: LegislativeChangeDao
) : LegislativeChangeRepository {

    private val gson = Gson()
    private val segmentListType = object : TypeToken<List<TextSegment>>() {}.type

    override fun getChanges(): Flow<List<LegislativeChange>> {
        return dao.getAllChanges()
            .onStart { refreshFromNetwork() }
            .map { entities -> entities.map { it.toDomain() } }
    }

    private suspend fun refreshFromNetwork() {
        try {
            val remote = api.getChanges()
            dao.clearAll()
            dao.insertAll(remote.map { it.toEntity() })
        } catch (_: Exception) {
            // Si no hay conexión, Room sigue emitiendo la caché local
        }
    }

    private fun LegislativeChangeDto.toEntity(): LegislativeChangeEntity {
        val current = currentSegments.map {
            val type = when (it.type.uppercase()) {
                "ADDED", "HIGHLIGHT" -> SegmentType.ADDED
                "REMOVED" -> SegmentType.REMOVED
                else -> SegmentType.UNCHANGED
            }
            TextSegment(type = type, text = it.text)
        }
        val previous = previousSegments.map {
            val type = when (it.type.uppercase()) {
                "REMOVED", "HIGHLIGHT" -> SegmentType.REMOVED
                "ADDED" -> SegmentType.ADDED
                else -> SegmentType.UNCHANGED
            }
            TextSegment(type = type, text = it.text)
        }
        return LegislativeChangeEntity(
            id = id,
            title = title,
            date = date,
            summary = description,
            currentSegmentsJson = gson.toJson(current),
            previousSegmentsJson = gson.toJson(previous)
        )
    }

    private fun LegislativeChangeEntity.toDomain(): LegislativeChange {
        val current: List<TextSegment> = gson.fromJson(currentSegmentsJson, segmentListType) ?: emptyList()
        val previous: List<TextSegment> = gson.fromJson(previousSegmentsJson, segmentListType) ?: emptyList()
        return LegislativeChange(
            id = id,
            title = title,
            date = date,
            description = summary,
            currentSegments = current,
            previousSegments = previous
        )
    }
}